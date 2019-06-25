package com.leqee.demo.utils;

import java.util.*;

public class SelfCollectionUtils {

    /**
     * Quick call
     * @param map
     * @return
     */
    public static List getListFromMapRecursion(Map map) {
        return (List) getListFromMapRecursion(map, true);
    }

    /**
     * 递归从map中的第一个Map.Entry中获取list,每次只取map的第一个
     * @param map
     * @param notNull true:如果没找到返回一个空集合  false:返回Null
     * @return
     */
    public static Object getListFromMapRecursion(Map map, boolean notNull) {
        Set<Map.Entry> set = map.entrySet();
        for (Map.Entry entry : set) {
            //only iterator once
            Object value = entry.getValue();
            if (value instanceof Map)
                return getListFromMapRecursion((Map) value, notNull);
            else if (value instanceof List)
                return value;
            else
                return notNull ? new ArrayList<>() : null;
        }
        return notNull ? new ArrayList<>() : null;
    }

    private static final class Test {

        public static void test1() {
            HashMap map1 = new HashMap();
            HashMap map11 = new HashMap();
            List list = new ArrayList<>();
            list.add(1);
            map1.put("map11", map11);
            map11.put("list", list);
            System.out.println(1);
        }

        public static void main(String[] args) {
        }
    }


}
