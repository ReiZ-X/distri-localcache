package com.reizx.localcache.core.configuration;

import com.reizx.localcache.api.broadcast.CacheBroadcaster;
import com.reizx.localcache.core.aop.CacheWriteAop;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author junke
 */
@Configuration
public class WriteConfiguration {

    @Bean
    @ConditionalOnBean(CacheBroadcaster.class)
    public CacheWriteAop cacheWriteAop(CacheBroadcaster broadcaster) {
        return new CacheWriteAop(broadcaster);
    }

}
