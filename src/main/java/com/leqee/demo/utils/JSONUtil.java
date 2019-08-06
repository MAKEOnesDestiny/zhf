package com.leqee.demo.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class JSONUtil {


    public static <T> List<T> findAllObjectRecursiveFromList(String key, List list, Class<T> tClass) {
        if (tClass == null) throw new IllegalArgumentException("tClass can't be null");
        //不支持数组类型
        if (tClass.getName().startsWith("[")) throw new UnsupportedOperationException("Array object is not supported");
        List resultList = new ArrayList();
        findAllObjectRecursiveFromList(key, list, resultList, tClass);
        return resultList;
    }

    private static <T> void findAllObjectRecursiveFromList(String key, List list, List resultList, Class<T> tClass) {
        for (Object obj : list) {
            if (tClass.isAssignableFrom(obj.getClass())) {
                resultList.add(obj);
            }
            if (List.class.isAssignableFrom(obj.getClass())) {
                findAllObjectRecursiveFromList(key, (List) obj, resultList, tClass);
            }
            if (Map.class.isAssignableFrom(obj.getClass())) {
                List result = findAllObjectRecursive(key, (Map<String, Object>) obj, tClass);
                if (result != null && result.size() != 0) {
                    resultList.addAll(result);
                }
            }
        }
    }

    public static <T> List<T> findAllObjectRecursive(String key, Map<String, Object> map, Class<T> tClass) {
        if (tClass == null) throw new IllegalArgumentException("tClass can't be null");
        //不支持数组类型
        if (tClass.getName().startsWith("[")) throw new UnsupportedOperationException("Array object is not supported");
        List<T> list = new ArrayList<>();
        tClass = adaptPrimitive(tClass);
        findAllObjectRecursive(key, map, list, tClass);
        return list;
    }

    private static <T> void findAllObjectRecursive(String key, Map<String, Object> map, List<T> list, Class<T> tClass) {
        Object obj = map.get(key);
        if (obj != null && tClass.isAssignableFrom(obj.getClass())) {
            list.add((T) obj);
        }
        for (Map.Entry e : map.entrySet()) {
            Object eValue = e.getValue();
            if (eValue instanceof Map) {
                //深度优先遍历
                findAllObjectRecursive(key, (Map<String, Object>) eValue, list, tClass);
            }
            if (eValue instanceof List) {
                findAllObjectRecursiveFromList(key, (List) eValue, list, tClass);
            }
        }
    }


    public static <T> T findObjectRecursiveFromList(String key, List list, Class<T> tClass) {
        if (list == null || list.size() == 0) return null;
        for (Object obj : list) {
            Object result = null;
            if (tClass.isAssignableFrom(obj.getClass())) {
                result = obj;
            }
            if (List.class.isAssignableFrom(obj.getClass())) {
                result = findObjectRecursiveFromList(key, (List) obj, tClass);
            }
            if (Map.class.isAssignableFrom(obj.getClass())) {
                result = findObjectRecursive(key, (Map<String, Object>) obj, tClass);
            }
            if (result != null) return (T) result;
        }
        return null;
    }

    public static <T> T findObjectRecursive(String key, Map<String, Object> map, Class<T> tClass) {
        if (tClass == null) throw new IllegalArgumentException("tClass can't be null");
        //不支持数组类型的检索
        if (tClass.getName().startsWith("["))
            throw new UnsupportedOperationException(tClass.getName() + " not supported!");
        tClass = adaptPrimitive(tClass);
        return findObjectRecursive0(key, map, tClass);
    }

    private static <T> T findObjectRecursive0(String key, Map<String, Object> map, Class<T> tClass) {
        Object obj = map.get(key);
        if (obj != null && obj.getClass().equals(tClass)) {
            return (T) obj;
        }
        for (Map.Entry e : map.entrySet()) {
            Object eValue = e.getValue();
            if (eValue instanceof Map) {
                Object result = findObjectRecursive0(key, (Map<String, Object>) eValue, tClass);
                //深度优先遍历
                if (result == null) continue;
                else return (T) result;
            }
        }
        //not found
        return null;
    }

    public static <T> Class<T> adaptPrimitive(Class<T> clazz) {
        if (clazz.isPrimitive()) {
            switch (clazz.getName()) {
                case "byte":
                    clazz = (Class<T>) Byte.class;
                    break;
                case "short":
                    clazz = (Class<T>) Short.class;
                    break;
                case "int":
                    clazz = (Class<T>) Integer.class;
                    break;
                case "long":
                    clazz = (Class<T>) Long.class;
                    break;
                case "float":
                    clazz = (Class<T>) Float.class;
                    break;
                case "double":
                    clazz = (Class<T>) Double.class;
                    break;
                case "boolean":
                    clazz = (Class<T>) Boolean.class;
                    break;
                case "char":
                    clazz = (Class<T>) Character.class;
                    break;
            }
        }
        return clazz;
    }


}
