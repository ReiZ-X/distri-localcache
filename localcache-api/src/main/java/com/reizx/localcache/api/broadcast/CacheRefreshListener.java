package com.reizx.localcache.api.broadcast;


import com.reizx.localcache.api.common.CacheChangeInfo;

/**
 * @author junke
 */
public interface CacheRefreshListener {

    /**
     * on broadcast message come
     */
    void onBroadcast(CacheChangeInfo info);
}
