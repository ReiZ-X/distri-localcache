package com.reizx.localcache.broadcast.rocketmq;

import com.alibaba.fastjson.JSON;
import com.reizx.localcache.api.broadcast.CacheBroadcaster;
import com.reizx.localcache.api.common.CacheChangeInfo;
import com.reizx.localcache.broadcast.rocketmq.properties.RkqChangerBroadCastConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

import java.nio.charset.StandardCharsets;

/**
 * @author junke
 */
@Slf4j
public class RocketMqBroadcaster implements CacheBroadcaster {
    public static final String BROAD_CASTER_TOPIC = "ability_localcache_broadcast";
    public static final String BROAD_CASTER_GROUP = "ability_localcache_broadcast_producer";

    private final DefaultMQProducer producer;

    public RocketMqBroadcaster(RkqChangerBroadCastConfig.RocketMqProperties properties) {
        this.producer = new DefaultMQProducer(BROAD_CASTER_GROUP);
        this.producer.setNamesrvAddr(properties.getNameAddr());
        try {
            this.producer.start();
        } catch (MQClientException exception) {
            throw new RuntimeException("broadcast rocketmq start failed.", exception);
        }
    }

    @Override
    public void broadcast(CacheChangeInfo cacheChangeInfo) {
        if (StringUtils.isBlank(cacheChangeInfo.getGroup())) {
            log.warn("blank group. skipped.");
            return;
        }
        if (StringUtils.isBlank(cacheChangeInfo.getKey())) {
            log.warn("blank key. skipped.");
            return;
        }
        byte[] bytes = JSON.toJSONString(cacheChangeInfo).getBytes(StandardCharsets.UTF_8);
        try {
            producer.send(new Message(BROAD_CASTER_TOPIC, bytes), new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    log.info("broadcast cache change info succeed., msgId:{}, cacheChangeInfo:{}", sendResult.getMsgId(), cacheChangeInfo);
                }

                @Override
                public void onException(Throwable e) {
                    log.error("!!!broadcast cache change info failed. cacheChangeInfo:{}", cacheChangeInfo, e);
                }
            });
        } catch (Exception e) {
            log.error("rocketmq send message failed. cacheChangeInfo:{}", cacheChangeInfo, e);
        }
    }
}
