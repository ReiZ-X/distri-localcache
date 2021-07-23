package com.reizx.localcache.examples.example1.write.service;

import com.reizx.localcache.core.aop.CacheChange;
import com.reizx.localcache.examples.example1.write.repo.UserInfo;
import com.reizx.localcache.examples.example1.write.repo.UserRepo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author junke
 */
@Service
public class AllService {
    @Resource
    private UserRepo userRepo;

    @CacheChange(group = "userInfo", key = "#userId")
    public void updateUser(Integer userId, String username) {
        userRepo.updateUsername(userId, username);
    }

    public UserInfo getUser(Integer userId) {
        return userRepo.getUserInfo(userId);
    }
}
