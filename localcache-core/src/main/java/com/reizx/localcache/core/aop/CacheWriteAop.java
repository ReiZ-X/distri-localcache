package com.reizx.localcache.core.aop;

import com.reizx.localcache.api.broadcast.CacheBroadcaster;
import com.reizx.localcache.api.common.CacheChangeInfo;
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
public class CacheWriteAop extends AbstractSpElAop {

    private final CacheBroadcaster broadcaster;

    public CacheWriteAop(CacheBroadcaster broadcaster) {
        this.broadcaster = broadcaster;
    }

    @Around("@annotation(cacheChange)")
    public Object cut(ProceedingJoinPoint point, CacheChange cacheChange) throws Throwable {
        Object result = point.proceed();
        try {
            StandardEvaluationContext standardEvaluationContext = varContext(point);
            String cacheKey = getElValue(cacheChange.key(), standardEvaluationContext);
            String cacheGroup = cacheChange.group();
            log.info("broadcast cacha change； group:{}, key:{}", cacheGroup, cacheKey);
            broadcaster.broadcast(new CacheChangeInfo(cacheGroup, cacheKey));
        } catch (Throwable t) {
            log.error("", t);
        }
        return result;
    }


}
