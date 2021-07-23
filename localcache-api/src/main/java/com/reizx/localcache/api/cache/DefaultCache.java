package com.reizx.localcache.api.cache;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author junke
 */
@Slf4j
public class DefaultCache implements LocalCacheOperation {
    private static final int SIZE_THRESHOLD = 100_000;
    /**
     * if data last write is before EXPIRE_MILLIS
     */
    private static final int EXPIRE_MILLIS = 7 * 24 * 3600 * 1000;
    private static final ConcurrentHashMap<String, ConcurrentHashMap<String, ValueInfo>> CACHES =
            new ConcurrentHashMap<>();
    public static final DefaultCache INST = new DefaultCache();

    @Override
    public String cacheType() {
        return "HASHMAP";
    }

    @Override
    public void removeCache(String group, String key) {
        ConcurrentHashMap<String, ValueInfo> kvs = CACHES.get(group);
        if (kvs != null) {
            kvs.remove(key);
        }
    }

    @Override
    public void putCache(String group, String key, Object obj) {
        CACHES.putIfAbsent(group, new ConcurrentHashMap<>(2 << 7));
        if (!CACHES.get(group).containsKey(key)) {
            if (currentSize() >= SIZE_THRESHOLD) {
                log.warn("local cache size exceed threshold：{},skip add key", SIZE_THRESHOLD);
            }
        }
        CACHES.get(group).put(key, new ValueInfo(obj));
    }

    @Override
    public Object getCache(String group, String key) {
        ConcurrentHashMap<String, ValueInfo> kvs = CACHES.get(group);
        if (null != kvs) {
            ValueInfo valueInfo = kvs.get(key);
            if (null != valueInfo) {
                if (isExpire(valueInfo)) {
                    kvs.remove(key);
                    return null;
                }
                return valueInfo.v;
            }
        }
        return null;
    }


    private static int currentSize() {
        LongAdder longAdder = new LongAdder();
        CACHES.values().forEach(v -> longAdder.add(v.size()));
        return longAdder.intValue();
    }

    private static boolean isExpire(ValueInfo info) {
        return System.currentTimeMillis() - info.ts > EXPIRE_MILLIS;
    }

    private DefaultCache() {
    }

    @Data
    @AllArgsConstructor
    public static class ValueInfo {
        public ValueInfo(Object v) {
            this.v = v;
            this.ts = System.currentTimeMillis();
        }

        private Object v;
        private Long ts;
    }

}
