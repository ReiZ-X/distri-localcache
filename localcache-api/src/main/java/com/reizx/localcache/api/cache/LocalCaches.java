package com.reizx.localcache.api.cache;

import lombok.extern.slf4j.Slf4j;

import java.util.ServiceLoader;

/**
 * @author junke
 */
@Slf4j
public class LocalCaches {

    private static final String CONFIG_KEY = "distribute_local_cache_type";
    private static LocalCacheOperation CACHES;

    static {
        String configKey = System.getProperty(CONFIG_KEY);
        if (!(null == configKey || configKey.isEmpty())) {
            ServiceLoader<LocalCacheOperation> services = ServiceLoader.load(LocalCacheOperation.class);
            for (LocalCacheOperation service : services) {
                if (service.cacheType().equalsIgnoreCase(configKey)) {
                    CACHES = service;
                }
            }
        }
        if (null == CACHES) {
            CACHES = DefaultCache.INST;
        }
    }

    public static void removeCache(String group, String key) {
        CACHES.removeCache(group, key);
    }

    public static void putCache(String group, String key, Object obj) {
        CACHES.putCache(group, key, obj);
    }

    public static Object getCache(String group, String key) {
        return CACHES.getCache(group, key);
    }
}
