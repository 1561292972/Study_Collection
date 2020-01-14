package com.list.test;

/**
 * @author leilei
 * @description:
 * @date 2020-01-07 10:02
 */
public class HashMapIndexFor {

    public static void main(String[] args) {
        //103&16-1=7
        //104&16-1=8
        //105&16-1=9
        //如果在hashmap频繁产生hash冲突或者index冲突   链表会很长 会导致查询效率降低
        //103&16=0
        //104&16=0
        //105&16=0 不去减1的话导致链表会很长而且会频繁发生index冲突问题链表会很长会导致查询效率降低           减1的话冲突会很少
        int index = indexFor(103, 16);
        System.out.println(index);
    }
    //根据hash值计算index下标位置
    static int indexFor(int h, int length) {
        return h & length - 1;
    }

    //回顾二进制金  通过01010101表示
    //十进制就是16    转换为二进制0001000      16除以二=8余数0 8除以二余数0 4除以2余数0   2除以2余数为0  1除以2余数为1  10000 表示为8位 所以是00010000
    //& 与运算  如果两者都为1 则为1否则为0  1&0=0  0&1=0  0&0=0  1&1=1
    //103二进制是01100111   16二进制是0001000   130&16=
    //01100111
    //00010000
    //=00000000
    //103&15 =
    //01100111
    //00001111
    //=00000111
    //103&15得出二进制位111  在我们程序里表示的为十进制     111转化为十进制为7
    //length - 1  变为奇数 减少冲突的概率
}