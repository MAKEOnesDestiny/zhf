package com.leqee.demo.utils;

import java.util.List;

public interface ExclusiveList<E> extends List<E>{

    boolean add(E e, RepeatStrategic<E> strategic);

    List reset();

    interface RepeatStrategic<E> {

        default boolean isEqual(E e1, E e2) {
            return e1 == null ? false : e1.equals(e2);
        }

        default void process(E e) throws Exception {
            throw new IllegalArgumentException("Repeat element:" + e);
        }

    }

}
