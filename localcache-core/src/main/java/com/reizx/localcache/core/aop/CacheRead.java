package com.reizx.localcache.core.aop;

import java.lang.annotation.*;

/**
 * @author junke
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CacheRead {
    String group();

    /**
     * springEL
     */
    String key();
}
