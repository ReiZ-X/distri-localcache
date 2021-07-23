package com.reizx.localcache.api.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author junke
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CacheChangeInfo {
    private String group;
    private String key;

    /**
     * default is change
     */
    private CacheChangeType changeType = CacheChangeType.CHANGE;
    /**
     * in some case, can broadcast newest data directly.
     * normally, only broadcast cache expired, need client refresh.
     */
    private Object obj;


    public CacheChangeInfo(String group, String key) {
        this.group = group;
        this.key = key;
    }

    public CacheChangeInfo(String group, String key, CacheChangeType changeType) {
        this.group = group;
        this.key = key;
        this.changeType = changeType;
    }

}
