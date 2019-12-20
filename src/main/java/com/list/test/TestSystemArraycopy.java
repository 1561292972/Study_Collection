package com.list.test;

/**
 * @author leilei
 * @description:
 * @date 2019-12-20 13:47
 */
public class TestSystemArraycopy {
    public static void main(String[] args) {
        Object[] objects=  new Object[]{"0","1","2","3"};
        //   Object[] object=  new Object[]{"1","2","3"};
        //src:源数组；        dest:目的数组；      destPos:目的数组放置的起始位置；length:复制的长度。
        int index=0; //下标
        //srcPos:源数组要复制的起始位置；
        int srcPos=index+1;
        //目的地数组赋值的长度 比如要删除2  4-2-1=1  2后面只有1个3
        int numMoved=objects.length-index-1;
        //  dest:目的数组；    destPos:目的数组放置的起始位置；    length:复制的长度。
        System.arraycopy(objects,srcPos,objects,index,numMoved);
        objects[objects.length-1]=null;
        for (int i = 0; i < objects.length; i++) {
            System.out.println(objects[i]);
        }


    }
}
