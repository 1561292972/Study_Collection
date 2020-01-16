package com.list.test;

import java.util.HashMap;

/**
 * @author leilei
 * @description:
 * @date 2020-01-14 10:55
 */
public class Map8Test {

    public static void main(String[] args) {
        /**
         * o(1) 耗时耗空间与输入数据的大小无关, 不管数据量怎么增加,耗时耗空间都不变       比如哈希算法 无论数据规模多大,都可以在一次计算后找到目标(不考虑冲突)
         * o(N) 数据量增加 查询耗时也会不断增加   链表就是O(n) 从头查到尾
         * o(log N)  表示2作为底数做平方    每次排除一般的可能  二分查找就是o(log N)
         */
        /***
         * ArrayList 根据下标查询的情况下,时间复杂度为o(1)
         * ArrayList 根据内容查询的情况下,时间复杂度为o(N)
         *
         * ArrayList  底层基于数组实现
         * LinkedList 底层基于链表实现
         *
         * LinkedList 根据下标查询的情况,时间复杂度o(N)
         * LinkedList 根据内容查询的情况下,时间复杂度为o(N)
         *
         * hashMap 根据key查询的情况下,时间复杂度为o(1)或者o(N)  没有发生index冲突
         */
        HashMap<Object, Object> map = new HashMap<>();
        //jdk7Hashmap 数组+链表  若index冲突过多的情况下 会导致查询慢 时间复杂度为0(N);
        //hashmap 根据Key查询 数据量增多会导致查询效率降低吗
        //hashmap根据key查询 根据获取hash值计算index
        /**
         * 两种情况;
         * 若果当前的key 没有发生index冲突问题 ,不管怎么增加hashmap中的数据  都是可以一次性查询出来  时间复杂度0(1)
         *
         * 如果当前的key 发生index冲突问题 形成了一个链表  需要从头遍历才可以查询
         */
        /**
         * java8为什么需要使用数组+链表+红黑树       如果index冲突过多,会导致单个链表过长,这时候查询效率非常慢时间复杂度o(N),
         * 若果链表长度>8的情况下  转换为红黑树存放,时间复杂度就会变成o(log N)
         */

    }
}
