package com.reizx.localcache.core.aop;

import com.reizx.localcache.api.cache.LocalCaches;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * @author junke
 */
@Slf4j
@Aspect
public class CacheReadAop extends AbstractSpElAop {

    @Around("@annotation(cacheRead)")
    public Object cut(ProceedingJoinPoint point, CacheRead cacheRead) throws Throwable {
        StandardEvaluationContext context = varContext(point);
        String key = getElValue(cacheRead.key(), context);
        String group = cacheRead.group();
        Object b = LocalCaches.getCache(group, key);
        if (null != b) {
            return b;
        }
        Object result = point.proceed();
        if (null != result) {
            LocalCaches.putCache(group, key, result);
        }
        return result;
    }
}
