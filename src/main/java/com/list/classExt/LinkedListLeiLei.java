package com.list.classExt;

import com.list.Interface.ListLeilei;

import java.util.LinkedList;

/**
 * @author leilei
 * @description:
 * @date 2019-12-25 16:05
 */
public class LinkedListLeiLei<E> implements ListLeilei<E> {
    //e  指的是存放的数据类型
    //集合的 大小 transient禁止序列化
    transient int size = 0;
    /**
     * first  为链表的第一个节点
     */
    transient LinkedListLeiLei.Node<E> first;
    /**
     * last   为链表的最后一个节点
     */
    transient LinkedListLeiLei.Node<E> last;


    public LinkedListLeiLei() {
    }


    @Override
    public boolean add(E e) {
        linkLast(e);
        return true;
    }

    /**
     * 添加我们的节点
     */
    void linkLast(E e) {
        //获取当前最后一个节点 用变量l  接收
        final LinkedListLeiLei.Node<E> l = last;
        //封装我们当前自定义元素 node(prev,element/item,next ) prev(上一个节点)=l  element(元素值)=e  next(下一个节点)=null
        final Node<E> newNode = new Node<>(l, e, null);
        //当前新增的节点肯定是链表中最后一个节点    把last节点赋值为新增的newNode节点
        last = newNode;
        //判断获取的链表最后有一个节点为不为null  为null说明当前新增的元素是第一个
        if (l == null) {
            //当前新增的元素是第一个
            first = newNode;
        } else {
            //有最后一个节点的话  就把最后一个节点的next赋值为新增的节点 这个时候新增的节点就位最后一个节点了
            l.next = newNode;
        }
        //长度加1
        size++;
        //modCount++;保证在system.out打印的时候 防止数据冲突问题 快速失败
    }


    //链表中的node 节点 底层是通过一个匿名内部类实现的
    private static class Node<E> {
        /**
         * 当前节点元素值  比如leiyuhang. 等等
         */
        E item;
        /**
         * 当前node的下一个node
         */
        LinkedListLeiLei.Node<E> next;
        /**
         * 当前节点的上一个node
         */
        LinkedListLeiLei.Node<E> prev;

        //使用构造函数传递参数
        Node(LinkedListLeiLei.Node<E> prev, E element, LinkedListLeiLei.Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }



















    @Override
    public E get(int index) {
        //检查index是否有越界
        checkElementIndex(index);
        return node(index).item;
    }
    private void checkElementIndex(int index) {
        if (!isElementIndex(index))
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }
    private boolean isElementIndex(int index) {
        return index >= 0 && index < size;
    }
    private String outOfBoundsMsg(int index) {
        return "Index: "+index+", Size: "+size;
    }


    /**
     * Returns the (non-null) Node at the specified element index.
     */
    LinkedListLeiLei.Node<E> node(int index) {
        // assert isElementIndex(index);
        //size >> 1=size/2  判断 要查找的下标小于 集合/2 的大小
        if (index < (size >> 1)) {
            //获取我们第一个节点
            LinkedListLeiLei.Node<E> x = first;
            for (int i = 0; i < index; i++) {
                //如果小于折半情况  从头查到index
                x = x.next;
            }
            return x;
        } else {
            //获取最后一个节点 ,倒过来查
            LinkedListLeiLei.Node<E> x = last;
            for (int i = size - 1; i > index; i--) {
                //如果大于折半情况  从尾查到index
                x = x.prev;
            }
            return x;
        }
        //1-10 条数据   查index=3 1-3之间查正着查询  查index=7的  10-7 倒着查询
    }






















    @Override
    public E remove(int index) {
        //检查index是否有越界
        checkElementIndex(index);
        return unlink(node(index));
    }
    /**
     * Unlinks non-null node x.
     */
    E unlink(LinkedListLeiLei.Node<E> x) {
        // assert x != null;
        final E element = x.item;
        //获取当前删除元素的下一个节点
        final LinkedListLeiLei.Node<E> next = x.next;
        //获取当前删除元素的上一个节点
        final LinkedListLeiLei.Node<E> prev = x.prev;
        //如果当前元素上一个node为null的情况下
        if (prev == null) {
            //prev的node为空的情况下, 就把next节点赋值为first第一个节点
            first = next;
        } else {
            //prev上一个的node不为空的情况下, 就把删除元素的上一个节的next赋值为当前删除节点的next节点
            prev.next = next;
            //删除的节点的上一个节点变为空   告诉GC实现回收
            x.prev = null;//目的是吧node本身的属性改为null 让GC回收 并不是把他的上一个node改为null
        }
        //如果当前元素下一个node为null的情况下
        if (next == null) {
            //把last最后一个node赋值为删除元素的prev上一个节点
            last = prev;
        } else {
            //把删除元素的下一个节点的prev赋值为删除元素的上一个节点
            next.prev = prev;
            //设置删除的这个节点next设置为null 利于GC回收
            x.next = null;
        }

        x.item = null;
        size--;
        return element;
    }


}
