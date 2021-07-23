package com.reizx.localcache.broadcast.rocketmq.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author junke
 */

@Data
@ConfigurationProperties(prefix = "distribute.localcache.broadcaster")
public class RkqChangerBroadCastConfig {
    private RocketMqProperties rocketmq;

    @Data
    public static class RocketMqProperties {
        /**
         * Rocketmq name server 地址
         */
        private String nameAddr;
    }

}
