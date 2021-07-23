package com.reizx.localcache.api.broadcast;

import com.reizx.localcache.api.common.CacheChangeInfo;

/**
 * @author junke
 */
public interface CacheBroadcaster {
    /**
     * broadcast cache change info.
     */
    void broadcast(CacheChangeInfo cacheChangeInfo);
}
