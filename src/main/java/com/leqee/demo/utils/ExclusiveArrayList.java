package com.leqee.demo.utils;

import java.util.*;

/**
 * 代理模式实现非重复
 */
public class ExclusiveArrayList<E> extends AbstractList<E> implements ExclusiveList<E> {

    //note: not thread-safe
    private List<E> target;

    public ExclusiveArrayList() {
        target = new ArrayList();
    }

    @Override
    public boolean add(E e, RepeatStrategic<E> strategic) {
        for (E s : target) {
            if (strategic.isEqual(e, s)) {
                return false;
            }
        }
        target.add(e);
        return true;
    }

    @Override
    public List reset() {
        target = new ArrayList<>();
        return this;
    }

    @Override
    public E get(int index) {
        return (E) target.get(index);
    }

    @Override
    public int size() {
        return target.size();
    }


}
