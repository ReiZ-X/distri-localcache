package com.reizx.localcache.examples.example1.write.repo;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author junke
 */
@Component
public class UserRepo {

    private Map<Integer, UserInfo> data = new HashMap<>(32);

    public void updateUsername(Integer userId, String username) {
        data.put(userId, new UserInfo(userId, username));
    }

    public UserInfo getUserInfo(Integer userId) {
        return data.get(userId);
    }
}
