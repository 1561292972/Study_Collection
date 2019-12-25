package com.list.test;

import com.list.classExt.LinkedListLeiLei;

/**
 * @author leilei
 * @description:
 * @date 2019-12-25 16:23
 */
public class TestLinkedListNode {

    //链表中的node 节点 底层是通过一个匿名内部类实现的
    private static class Node<E> {
        /**
         * 当前节点元素值  比如leiyuhang. 等等
         */
        E item;
        /**
         * 当前node的下一个node
         */
        TestLinkedListNode.Node<E> next;
        /**
         *当前节点的上一个node
         */
        TestLinkedListNode.Node<E> prev;

        //使用构造函数传递参数
        Node(TestLinkedListNode.Node<E> prev, E element, TestLinkedListNode.Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }

        //使用构造函数传递参数
        Node( E element) {
            this.item = element;
        }

        public void setNext(Node<E> next) {
            this.next = next;
        }

        public void setPrev(Node<E> prev) {
            this.prev = prev;
        }
    }



    public static void main(String[] args) {
        Node node1 = new Node("第一关游戏");
        Node node2 = new Node("第二关游戏");
        //node1关联下一关next 为Node2
        node1.next=node2;
        //node2关联上一关就是prev 为Node1
        node2.prev=node1;
        System.out.println("node:"+node1);
    }
}
