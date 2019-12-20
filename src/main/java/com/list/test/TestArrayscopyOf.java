package com.list.test;

import java.util.Arrays;

/**
 * @author leilei
 * @description:
 * @date 2019-12-20 13:46
 */
public class TestArrayscopyOf {
    public static void main(String[] args) {

        //长度为3
        Object[] elementData={"1","2","3"};
        System.out.println("数组扩容前:");
        for (int i = 0; i < elementData.length; i++) {
            System.out.println(elementData[i]);
        }
        int newCapacity=10;
        //返回新的数组
        Object[] objects = Arrays.copyOf(elementData, newCapacity);
        System.out.println("数组扩容后:");
        for (int i = 0; i < objects.length; i++) {
            System.out.println(objects[i]);
        }
        /**
         *  Arrays.copyOf
         *  对我们数组实现扩容,将我们以前的旧数据,复制到我们的新数组中
         */
    }
}
