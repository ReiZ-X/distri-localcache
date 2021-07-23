package com.reizx.localcache.examples.example1.read.controller;

import com.reizx.localcache.examples.example1.read.service.ReadService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author junke
 */
@RestController
public class TestController {
    private ReadService readService;

    public TestController(ReadService readService) {
        this.readService = readService;
    }

    @GetMapping("getUser")
    public Object read(@RequestParam("userId") Integer userId) {
        return readService.queryUserInfo(userId);
    }

}
