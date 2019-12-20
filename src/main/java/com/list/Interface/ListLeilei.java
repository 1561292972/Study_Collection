package com.list.Interface;

/**
 * @author leilei
 * @description:
 * @date 2019-12-18 16:50
 */
public interface ListLeilei<E> {

    boolean add(E e);

    E get(int index);

    E remove(int index);
}
