package com.list.classExt;

import com.list.Interface.MapLeilei;

import java.awt.event.KeyEvent;
import java.security.Key;
import java.util.LinkedList;

/**
 * @author leilei
 * @description:
 * @date 2019-12-31 10:45
 */
public class LinkedListHashMapLeilei<K,V> implements MapLeilei<K,V> {

    LinkedList<Entry>[] object=new LinkedList[100];

/*    Object[] object=new Object[100];*/

    @Override
    public int size() {
        return 0;
    }

    @Override
    public V put(K key, V value) {


        //计算在数组中存放的下标
        int hash = hash(key);
        //先查询出相同的hashCode的key在linkedList中是否存在
        LinkedList<Entry> linkedList = object[hash];
        Entry entry = new Entry(key, value);
        if(linkedList==null){
            //说明没有产生hashCode冲突问题
            linkedList=new LinkedList();
            linkedList.add(entry);
            object[hash]=linkedList;
            return value;
        }
        //说明hashCode产生冲突了 先使用equals key值是否相同, 判断是否是需要修改对应的value
        for(Entry ey:linkedList){
            //hashCode相同 equals值也相同  修改key对应的value
            if(ey.k.equals(key)){
                ey.setValue(value);
                return value;
            }
        }
        //hashCode相同 equals对象不相同 那就不是修改,添加进链表里
        linkedList.add(entry);
        return value;
    }
    @Override
    public V get(K key) {
        int hash = hash(key);
        LinkedList<Entry> linkedList =object[hash];
        for(Entry ey:linkedList){
            //hashCode相同 equals值也相同  修改key对应的value
            if(ey.getKey().equals(key)){
                return (V) ey.getValue();
            }
        }
        return null;
    }












    //Entry就是HashMap中的Node
    class Entry<K,V> implements MapLeilei.Entry<K,V>{
        /**
         * Key
         */
        private K k;
        /**
         * value
         */
        private V v;

        /**
         * 使用有参构造函数赋值
         * @param k
         * @param v
         */
        public Entry(K k, V v) {
            this.k = k;
            this.v = v;
        }





        @Override
        public K getKey() {
            return k;
        }

        @Override
        public V getValue() {
            return v;
        }

        @Override
        public V setValue(V value) {
            this.v= (V) value;
            return v;
        }
    }



    private int hash(K key){
        //根绝key的hashCode  % object.length  获取数组中存放的小标位置 hash
        int hashCode = key.hashCode();
        int hash = hashCode % object.length;
        return hash;
    }

}
