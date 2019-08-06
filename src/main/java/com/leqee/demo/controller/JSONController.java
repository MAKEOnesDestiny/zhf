package com.leqee.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.leqee.demo.utils.JSONUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Log4j2
public class JSONController {


    @RequestMapping(value = "/parse/json")
    public Object getFile(@RequestParam("a") String var1, @RequestParam("b") String var2) {
       /* List list = JSONUtil.findAllObjectRecursive("name", jsonObject, List.class);
        return list;*/
        return "aaa";
    }

    public static void main(String[] args) {
        Map map = new HashMap();
        map.put("name", 1);
        Integer i = JSONUtil.findObjectRecursive("name", map, int.class);
        System.out.println(i);
    }

}
