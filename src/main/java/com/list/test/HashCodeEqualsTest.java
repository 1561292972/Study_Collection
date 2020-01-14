package com.list.test;

/**
 * @author leilei
 * @description:
 * @date 2019-12-31 10:48
 */
public class HashCodeEqualsTest {
    /**
     *  hashCode与equals的区别
     *  hashCode:是JDK根据对象的地址或者字符串或者数字计算出来的int类型的数值     主要用于hashMap快速查找
     *  hashCode与equals都可以比较对象的区别
     *
     *                  两个对象的hashCode相同, 但是对象的值不一定相同
     *
     *                  两个对象使用equal比较, 那么对象的值一定相同
     */

    public static void main(String[] args) {
        //a阿斯克码值为97
        String  a="a";
        Integer b=new Integer(97);
        System.out.println(a.hashCode());//97
        System.out.println(b.hashCode());//97  两个对象的hashCode相同, 但是对象的值不一定相同


    }

}
