package com.list.classExt;

import com.list.Interface.MapLeilei;
import org.omg.CORBA.PRIVATE_MEMBER;

import java.util.ArrayList;
import java.util.List;

/**
 * @author leilei
 * @description:   基于ArrayList 实现HashMap集合
 * @date 2019-12-31 9:53
 */
public class ArrayListHashMapLeilei<K,V> implements MapLeilei<K,V> {
    /**
     * 用于存放我们所有的键值对集合
     * hashmap  存放数据 都会包含key value       可以使用一个对象包装
     * Entry 表示就是我们存放在hashmap中的一条键值对
     */
    private  List<Entry<K,V>> listEntry=new ArrayList<>();

    @Override
    public int size() {
        return listEntry.size();
    }





    @Override
    public V put(K key, V value) {
        //1:封装Entry对象
        Entry<K, V> entry = new Entry<>(key,value);
        listEntry.add(entry);
        return value;
    }



    @Override
    public V get(K key) {
        for (Entry entry:listEntry){
            //遍历集合中的Entry 若果key 与传进来的key相等直接返回
            if(entry.k.equals(key)){
                return (V) entry.getValue();
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
}
