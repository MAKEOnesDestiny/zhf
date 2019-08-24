package com.leqee.demo.controller;

import com.leqee.demo.bean.TestBean;
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

    @ResponseBody
    @RequestMapping("/test2")
    public String test2(TestBean testBean){
        System.out.println(testBean);
        return null;
    }

    @ResponseBody
    @RequestMapping("/test3")
    public String test3(@MyModelAttribute TestBean testBean){
        System.out.println(testBean);
        return "123";
    }

    @ResponseBody
    @RequestMapping("/test4")
    public String test4(TestBean testBean){
        System.out.println(testBean);
        return "123";
    }

}
