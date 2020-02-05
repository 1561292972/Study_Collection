package com.list.test;

import com.list.classExt.MyConsurrentHashMap;

import java.util.Hashtable;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author leilei
 * @description:
 * @date 2020-01-20 10:27
 */
public class ConcurrentHashMapLeilei {
    /**
     * HashTable 与 HashMap的区别
     * HashTable 线程安全     不允许存放key为null      d底层采用synchronized解决线程安全问题把整个table锁住  保证多线程下只允许一个线程做put操作,这样多线程最终变为单线程执行 效率非常低
     * HashMap   线程不安全   允许key为null(数组第0个位置)   线程不安全会出现脏读情况
     * 为了既能够 线程安全  多线程下提高效率 就采用ConcurrentHashMap
     */
    public static void main(String[] args) {
        /**
         * ConcurrentHashMap 默认分成16个不同的小的hashTble   然后在通过计算方式,在多线程情况下,让每个键值对到不同的hashTble存放,从而能够体现多线程效率 问题,也能够保证线程安全的问题
         *
         * 注意理论上最多只有16个线程能够同时实现对ConcurrentHashMap操作
         * 1.7 ConcurrentHashMap 采用分段锁
         * 1.8 ConcurrentHashMap 删除分段锁技术 改用无CAS无锁机制+synchronized
         */
        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();


        MyConsurrentHashMap<String,String>  stringStringMyConsurrentHashMap=new MyConsurrentHashMap();
        stringStringMyConsurrentHashMap.put("蕾蕾","蚂蚁课堂");
        System.out.println(stringStringMyConsurrentHashMap.get("蕾蕾"));
        /**
         * 1.7 ConcurrentHashMap源码 采用分段锁的机制,分为多个不同的Segment/分成16个不同的Segment  每个Segment都有自己独立的锁
         * 分成16个不同的Segment,每个Segment是独立的table,Segment其实就是我们的hashtable,
         * 但是 Segment底层是继承了Reentrantlock去上锁
         * 根据key计算index位置,就是存放在哪个Segment里
         * ConcurrentHashMap->Segment(16个 hashtable)->hashEntry
         * jdk1.7hashmap存在什么多线程每次扩容会存在死循环问题  Segment里肯定没有这个问题存在因为上了重入锁
         *
         */
        /**
         * 1.8 ConcurrentHashMap源码 采用 CAS无锁机制
         * new Node采用cas乐观锁机制保证线程安全问题
         * 如果计算index产生了冲突,使用synchronized上锁
         * 锁的粒度更加精细锁的事node节点
         */

    }
}
