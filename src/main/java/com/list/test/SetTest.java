package com.list.test;

import java.util.HashSet;

/**
 * @author leilei
 * @description:
 * @date 2019-12-31 15:25
 */
public class SetTest {
    public static void main(String[] args) {
        //不允许重复,  保证key的幂等性问题(唯一性问题)  无顺序       支持存放为null
        HashSet<String> objects = new HashSet<>();
        objects.add("我牛逼");
        objects.add("你牛逼");
        objects.add("我牛逼");
        System.out.println(objects.size());

        /**
         * HashSet 底层如何实现的
         *  1.hashSet底层add的实现 包装了hashMap集合  添加的元素为hashMap的key  value值为占位的对象为的是填充map的value
         *
         * HashSet 底层如何保证key不允许重复
         *  1.hashSet包装了HashMap  hashSet继承了HashMap的特性 hashmap中的key是唯一的, 所以hashSet元素的值也是唯一的
         *    hashmap底层是如何保证key不允许重复呢    底层通过比较hashCode相同而且equals 比较对象值也相同  说明是两个相同的key这个时候会进行覆盖掉的
         *
         *
         * HashSet不能通过下标去取值
         */
    }
}
