package com.reizx.localcache.examples.example1.read.service;

import com.reizx.localcache.core.aop.CacheRead;
import com.reizx.localcache.examples.example1.read.util.HttpUtil;
import org.springframework.stereotype.Service;

/**
 * @author junke
 */
@Service
public class ReadService {


    @CacheRead(group = "userInfo", key = "#userId")
    public String queryUserInfo(Integer userId) {
        return HttpUtil.get("http://localhost:8081/getUser?userId=" + userId);
    }

}
