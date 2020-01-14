package com.list.test;

import com.list.classExt.ArrayListHashMapLeilei;
import com.list.classExt.HashMapLeilei;
import com.list.classExt.LinkedListHashMapLeilei;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author leilei
 * @description:
 * @date 2019-12-31 9:50
 */
public class MapTest {
    public static void main(String[] args) {
/*        //基于ArrayList 实现HashMap集合   这种情况ArrayList缺点:get 的时候效率不好  put key重复问题         这是通过数组实现的
        ArrayListHashMapLeilei<Object, Object> arrayListHashMapLeilei = new ArrayListHashMapLeilei<>();
        arrayListHashMapLeilei.put("leilei","牛逼");
        System.out.println(arrayListHashMapLeilei.get("leilei"));*/

/*        //基于LinkedList 实现HashMap集合
        LinkedListHashMapLeilei<Object, Object> linkedListHashMapLeilei = new LinkedListHashMapLeilei<>();
        linkedListHashMapLeilei.put("a","牛逼");
        linkedListHashMapLeilei.put(97,"牛逼a");
        System.out.println(linkedListHashMapLeilei.get(97));*/
        /**
         * 思考问题
         * 万一对象不同 ,但是hashcode相同的情况下  put 存放数据的时候可能会产生覆盖的问题
         * 答案:使用链表技术
         *
         * 如何解决hashcode冲突的问题
         * 答案:使用链表技术
         * 如果两个对象不同,但是hashCode相同,  底层通过   一个链表存放相同的 hashcode 对象
         *
         * 如何实现查询
         * 定位到相同的linkedList集合  遍历集合 如果key等于存放的值 情况下就返回value对象
         *
         * hashMap put方法如何实现
         * 1: 使用ket.hashcode 计算hash值存放到数组位置
         * 2:如果发生key的hashcode相同 使用链表实现存放 如果hashcode相同且equlas也相同,直接覆盖元素
         *
         * 以上是hashmap的思想
         */


      /*  HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("leilei","牛逼");
        System.out.println(hashMap.get("leilei"));*/
        /**
         * 分析步骤 : 1 构造函数   2 具体方法
         *
         * jdk7  hashMap 底层  数组+链表
         * jdk8  hashMap 底层  数组+链表+红黑树
         *
         *
         * 为什么 jdk7 数组+链表    链表分为单向链表和双向链表  hashMap使用单向链表
         * HashMap是一个键值对集合 key和value      使用entry对象封装键值对
         * jdk7 的时候Entry对象封装键值对     key value next(指向下一个)
         * jdk8 叫做node
         *
         * 默认加载因子0.75  默认数组容量位16  阈值就是16*0.75=12 也就是达到12的时候开始扩容  还没满的时候开始扩容
         * 如果加载因子为1的情况  数组容量位16  阈值也为16 也就是达到16才开始扩容 (加载因子越大,index下标冲突概率也就大  链表会很长 空间利用率非常高)
         * 如果加载因子为0.1的情况  会频繁进行扩容  冲突会小点,毕竟每次都在进行扩容  但是空间利用率非常底)
         * 想冲突机会小,空间利用率必须设置成0.75 写map专家定的
         */
        String a="a";
        Integer b=new Integer("97");//产生hash冲突
        HashMapLeilei<Object, Object> mapLeilei = new HashMapLeilei<>();
        for (int i=1;i<=12;i++){
            mapLeilei.put("leilei_"+i,"leilei_"+i);
        }
        mapLeilei.put("A","雷雷A");


        /**
         * hashMap 默认初始化的容量位多少 ?                      16
         * hashMap 如何解决hashcode冲突问题                      通过链表
         * hashMap 线程是否安全  与hashTable之间的区别
         * hashMap 是否可以存放key为空的对象                     支持 存放在数组的第0个位置或者第一个链表中
         * hashMap 底层如何实现减少index 下标冲突的问题
         * hashMap 底层实现扩容的时候存在哪些问题
         * hashMap 是否可以存放键值对为自定义对象               可以 因为是泛型
         * hashMap 加载因子为什么是0.75而不是其他
         *
         *
         * hashMap 在jdk7存在哪些问题                           线程不安全 链表过长导致查询效率低,  底层扩容会存在死循环的问题
         * jdk7计算hash值非常均摊 为的是减少hash冲突问题,因为用的链表解决冲突,链表查询效率低 减少hash冲突
         * jdk8计算hash值非常简单 存在hash冲突概率比jdk7大 但是没有关系因为java8采用红黑树查询
         *
         *
         *
         *
         * hashMap put方法如何实现,
         *
         * hashMap index冲突与hash冲突存在哪些区别
         * index冲突的原因底层做二进制运算的产生相同的index,对象不同但是二进制运算是相同的       &length-1必须要是奇数
         * hash冲突对象不同  但是hashcode相同   确保hashMap中为了确保相同的key 使用equals比较
         *
         * index 冲突惠存在哪些问题?                             会导致值覆盖掉,所以使用链表进行解决
         * 同一个链表中存放哪些内容?                             index冲突或者hash冲突
         * hashMap 链表使用单向还是双向                          单向
         * 如果hashMap中链表长度过长存在哪些问题                 会导致整个查询效率降低,时间复杂度为0(n)
         *
         *  hashMap key为空                                      存放在数组的第0个位置或者第一个链表中
         *
         *  jdk7中的hashmap 根据key查询时间复杂度为多少
         *  o1容量的增大也不会影响我的查询
         *  1. 如果key没有发生hash冲突 可以直接根绝key计算index直接从数组中查询
         *  2. 如果该key存在hash重冲突问题的  会形成一个链表 查询效率会非常低
         *
         *  hashmap 每次默认情况下扩容多少倍                       每次是2倍
         */










        /**
         * hashmap在并发的情况下,扩容会存在哪些问题
         * 在jdk7版本  使用链表头插赋值方法,在多线程的情况下会导致死循环的问题
         * table 长度为16
         * a hash值是2 计算index=2&table.leght(16)
         *原因分析:
         * 因为每次数组在扩容的时候,新的数组长度发生了变化  需要重新计算index值;
         *         // 获取新的tables的长度为32
         *         int newCapacity = newTable.length;
         *         // 遍历原来tables中的所有链表
         *         for (Entry<K, V> e : tables) {
         *             // 遍历单个链表
         *             while (null != e) {
         *                 // 获取下一个next
         *                 Entry<K, V> next = e.next;
         *                 // 计算原来key的index 在新的table中位置
         *                 int index = indexFor(e.hash, newCapacity);
         *                 //目的获取当前的index在新的table中是否有冲突
         *                 ///这行代码不安全 在多线程情况下,同时对HashMap实现扩容,因为每次数组在扩容的时候,新的数组长度发生了变化,需要重新计算index值;
         *                 //还要将原来的table中的数据移动到新的table中,在hashmap1.7版本598h行代码,  e.next=newTable[i];直接改变原来的next关系;导致出现脏读的数据;
         *                 //解决用hashTable   counrntHashmap
         *                 e.next = newTable[index];      /
         *                 newTable[index] = e;
         *                 // 继续遍历下一个节点
         *                 e = next;
         *             }
         *         }
         *
         *
         *
         * 在jdk1.8已经解决了该问题
         *
         */

    }
}
