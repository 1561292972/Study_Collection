package com.list.test;

import com.list.Interface.ListLeilei;
import com.list.classExt.ArrayListLeiLei;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author leilei
 * @description:
 * @date 2019-12-18 16:53
 */
public class ListTest {
    public static void main(String[] args) {
        //ArrayListLeiLei
        ListLeilei<String> list = new ArrayListLeiLei<>();
        list.add("1");
        list.add("2");
        int oldCapacity=26;
        System.out.println(oldCapacity >> 1);

        //ArrayListLeiLei


    }
}
