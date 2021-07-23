package com.reizx.localcache.broadcast.rocketmq;

import com.alibaba.fastjson.JSON;
import com.reizx.localcache.api.broadcast.CacheRefreshListener;
import com.reizx.localcache.api.cache.LocalCaches;
import com.reizx.localcache.api.common.CacheChangeInfo;
import com.reizx.localcache.api.common.CacheChangeType;
import com.reizx.localcache.broadcast.rocketmq.properties.RkqListenerBroadCastConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author junke
 */
@Slf4j
public class RocketMqListener implements CacheRefreshListener {
    public static final String CONSUME_GROUP = "ability_localcache_broadcast_consumer";

    public RocketMqListener(RkqListenerBroadCastConfig.RocketMqProperties properties) {
        try {
            DefaultMQPushConsumer pushConsumer = new DefaultMQPushConsumer();
            pushConsumer.setNamesrvAddr(properties.getNameAddr());
            pushConsumer.setConsumerGroup(CONSUME_GROUP);
            pushConsumer.setMessageModel(MessageModel.BROADCASTING);
            pushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
            pushConsumer.subscribe(RocketMqBroadcaster.BROAD_CASTER_TOPIC, "*");
            pushConsumer.registerMessageListener(new MessageListenerConcurrently() {
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                    try {
                        for (MessageExt msg : msgs) {
                            CacheChangeInfo cacheChangeInfo = JSON.parseObject(new String(msg.getBody(), StandardCharsets.UTF_8), CacheChangeInfo.class);
                            log.info("接收到缓存变更通知. info:{}", cacheChangeInfo);
                            onBroadcast(cacheChangeInfo);
                        }
                    } catch (Throwable t) {
                        log.error("消费缓存变动通知失败", t);
                    }
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
            });
            pushConsumer.start();
        } catch (Exception e) {
            log.error("初始化缓存变动监听consumer失败.", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onBroadcast(CacheChangeInfo cacheChangeInfo) {
        CacheChangeType changeType = cacheChangeInfo.getChangeType();
        switch (changeType) {
            case CHANGE: {
                if (null != cacheChangeInfo.getObj()) {
                    LocalCaches.putCache(cacheChangeInfo.getGroup(), cacheChangeInfo.getKey(), cacheChangeInfo.getObj());
                } else {
                    LocalCaches.removeCache(cacheChangeInfo.getGroup(), cacheChangeInfo.getKey());
                }
                break;
            }
            case REMOVE: {
                LocalCaches.removeCache(cacheChangeInfo.getGroup(), cacheChangeInfo.getKey());
                break;
            }
            default:
                break;
        }
    }

}
