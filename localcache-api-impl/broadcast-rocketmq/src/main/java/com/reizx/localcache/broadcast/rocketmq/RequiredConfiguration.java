package com.reizx.localcache.broadcast.rocketmq;

import com.reizx.localcache.api.broadcast.CacheBroadcaster;
import com.reizx.localcache.api.broadcast.CacheRefreshListener;
import com.reizx.localcache.broadcast.rocketmq.properties.RkqChangerBroadCastConfig;
import com.reizx.localcache.broadcast.rocketmq.properties.RkqListenerBroadCastConfig;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author junke
 */
@Configuration
@EnableConfigurationProperties(value = {RkqChangerBroadCastConfig.class, RkqListenerBroadCastConfig.class})
public class RequiredConfiguration {

    @Bean
    @ConditionalOnMissingBean(CacheRefreshListener.class)
    public RocketMqListener rocketMqListener(RkqListenerBroadCastConfig rkqListenerBroadCastConfig) {
        RkqListenerBroadCastConfig.RocketMqProperties config = rkqListenerBroadCastConfig.getRocketmq();
        if (null != config && StringUtils.isNotBlank(config.getNameAddr())) {
            return new RocketMqListener(config);
        }
        return null;
    }

    @Bean
    @ConditionalOnMissingBean(CacheBroadcaster.class)
    public RocketMqBroadcaster rocketMqBroadcaster(RkqChangerBroadCastConfig rkqChangerBroadCastConfig) {
        RkqChangerBroadCastConfig.RocketMqProperties config = rkqChangerBroadCastConfig.getRocketmq();
        if (null != config && StringUtils.isNotBlank(config.getNameAddr())) {
            return new RocketMqBroadcaster(config);
        }
        return null;
    }
}
