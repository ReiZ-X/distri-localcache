package com.reizx.localcache.core.configuration;

import com.reizx.localcache.api.broadcast.CacheRefreshListener;
import com.reizx.localcache.core.aop.CacheReadAop;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author junke
 */
@Configuration
public class ReadConfiguration {

    @Bean
    @ConditionalOnBean(CacheRefreshListener.class)
    public CacheReadAop cacheReadAop() {
        return new CacheReadAop();
    }

}
