package com.leqee.demo.controller;

import lombok.Builder;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/demo")
@Lazy
@Builder
public class TestController {

    @ResponseBody
    @GetMapping("/test")
    public String test(){
        int a = 10;
        Object aa = a;
        return null;
    }

}
