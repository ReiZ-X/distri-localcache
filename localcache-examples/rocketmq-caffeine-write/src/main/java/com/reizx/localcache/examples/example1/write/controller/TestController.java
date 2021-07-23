package com.reizx.localcache.examples.example1.write.controller;

import com.reizx.localcache.examples.example1.write.service.AllService;
import org.springframework.web.bind.annotation.*;

/**
 * @author junke
 */
@RestController
public class TestController {
    private AllService allService;

    public TestController(AllService allService) {
        this.allService = allService;
    }

    @ResponseBody
    @GetMapping("getUser")
    public Object read(@RequestParam("userId") Integer userId) {
        System.out.println("stop querying...");
        return allService.getUser(userId);
    }

    @PostMapping("updateUser")
    public Object write(@RequestParam("userId") Integer userId, @RequestParam("username") String username) {
        allService.updateUser(userId, username);
        return "success";
    }

}
