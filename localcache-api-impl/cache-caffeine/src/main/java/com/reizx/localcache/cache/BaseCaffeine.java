package com.reizx.localcache.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.reizx.localcache.api.cache.LocalCacheOperation;

import java.time.Duration;

/**
 * @author junke
 */
public class BaseCaffeine implements LocalCacheOperation {

    private static final Cache<String, Object> LOCAL_CACHE;

    static {
        LOCAL_CACHE = Caffeine.newBuilder()
                .maximumSize(100_000)
                .expireAfterAccess(Duration.ofHours(1))
                .build();
    }

    @Override
    public String cacheType() {
        return "caffeine";
    }

    @Override
    public void removeCache(String group, String key) {
        LOCAL_CACHE.invalidate(cacheKey(group, key));
    }

    @Override
    public void putCache(String group, String key, Object obj) {
        LOCAL_CACHE.put(cacheKey(group, key), obj);
    }

    @Override
    public Object getCache(String group, String key) {
        return LOCAL_CACHE.getIfPresent(cacheKey(group, key));
    }

    private String cacheKey(String group, String key) {
        return "@" + group + "#" + key;
    }
}
