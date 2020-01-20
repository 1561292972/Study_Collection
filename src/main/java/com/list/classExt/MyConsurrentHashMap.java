package com.list.classExt;

import java.util.Hashtable;

/**
 * @author leilei
 * @description: 1.7版本
 * @date 2020-01-20 11:33
 */
public class MyConsurrentHashMap<K,V> {
    /**
     * 分成16个不同的hashtables
     */
    private Hashtable<K,V>[] hashtables;

    public MyConsurrentHashMap(){
        //默认初始大小为16
        hashtables=new Hashtable[16];
    }

    public void  put(K k,V v){
        //1.获取hash值
        int hash = hash(k);
        //2.计算index 存在哪个hashtable里面
        int index=hash & hashtables.length-1;
        //3.获取对应的hashtable
        Hashtable hashtable = hashtables[index];
        if(hashtable==null){
            //多线程同时new 可能存在Hashtable
            hashtable=new Hashtable();
            //用cas 阈值   预热机制(提前把每个小的hashtable初始化)
        }
        hashtable.put(k,v);
        hashtables[index]=hashtable;
    }
    public V  get(K k,V v){
        //1.获取hash值
        int hash = hash(k);
        //2.计算index 存在哪个hashtable里面
        int index=hash & hashtables.length-1;
        //3.获取对应的hashtable
        Hashtable hashtable = hashtables[index];
        if (hashtable==null){
            return null;
        }
        return (V) hashtable.get(k);
    }

    private int hash(Object k){
        return 0 ^ k.hashCode();
    }
}
