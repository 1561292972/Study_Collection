package com.list.test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author leilei
 * @description:
 * @date 2019-12-20 15:18
 */
public class TestFailFast {

    List list=new ArrayList();
    public static void main(String[] args) {
        //fail-Fast 机制
        new TestFailFast().startRun();
    }

    void startRun(){
        new Thread(new Thread01()).start();
        new Thread(new Thread02()).start();
    }
    void print(){
        //点forEach 去ArrayList 集合中查看forEach
        list.forEach(e -> System.out.println(e));
    }
    class Thread01 extends Thread{
        @Override
        public void run() {
            for(int i=0;i<10;i++){
                list.add(i);
                print();
            }
        }
    }

    class Thread02 extends Thread{
        @Override
        public void run() {
            for(int i=0;i<10;i++){
                list.add(i);
                print();
            }
        }
    }
}
