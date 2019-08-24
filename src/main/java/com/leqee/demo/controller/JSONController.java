package com.leqee.demo.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.leqee.demo.utils.JSONUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.leqee.demo.utils.JSONUtil.*;

@RestController
@Log4j2
public class JSONController {

    @RequestMapping(value = "/parse/json3")
    public Object json3(@RequestBody JSONObject json) {
//        Path path0 = new JSONUtil.MapFirstEnterPath()
        Path path = new JSONUtil.MapFirstEnterPath(2);
        SearchMap searchMap = new SearchMap(json);
        Goal goal = new Goal(Integer.class);
        return JSONUtil.findAllObject(searchMap, goal, path);
    }


    @RequestMapping(value = "/parse/json2")
    public Object getFile(@RequestParam("a") String var1, @RequestParam("b") String var2) {
       /* List list = JSONUtil.findAllObjectRecursive("name", jsonObject, List.class);
        return list;*/
        return "aaa";
    }

    @RequestMapping(value = "/parse/json")
    public Object getFile(@RequestBody JSONObject jsonObject) {
        List list = JSONUtil.findAllObjectRecursive(null, jsonObject, Map.class);
       /* List list = JSONUtil.findAllObjectRecursive("name", jsonObject, List.class);
        return list;*/
        return list;
    }

    @RequestMapping(value = "/parse/array")
    public Object getFile(@RequestBody JSONArray jsonArray) {
       /* List list = JSONUtil.findAllObjectRecursive("name", jsonObject, List.class);
        return list;*/
        return "aaa";
    }

    public static void main(String[] args) {
        Map map = new HashMap();
        map.put("name", -1);
        map.put("name1", 1);
        map.put("name2", 2);
        map.put("name3", 3);
        map.put("name0", 0);
        for (Object entry : map.entrySet()) {
            entry = (Map.Entry) entry;
            System.out.println(((Map.Entry) entry).getKey());
            System.out.println(((Map.Entry) entry).getValue());
        }
    }

}
