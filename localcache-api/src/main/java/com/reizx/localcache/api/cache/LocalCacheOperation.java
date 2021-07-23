package com.reizx.localcache.api.cache;

/**
 * @author junke
 */
public interface LocalCacheOperation {

    /**
     * @return cache type
     */
    String cacheType();

    /**
     * remove cache
     *
     * @param group e.g: userInfo
     * @param key   e.g：1
     */
    void removeCache(String group, String key);

    /**
     * put cache
     *
     * @param group e.g ：userInfo
     * @param key   e.g：52
     * @param obj   cache value
     */
    void putCache(String group, String key, Object obj);

    /**
     * get cache
     *
     * @param group e.g: userInfo
     * @param key   e.g：52
     * @return cache obj
     */
    Object getCache(String group, String key);
}
