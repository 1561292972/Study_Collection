package com.list.Interface;

/**
 * @author leilei
 * @description:
 * @date 2019-12-31 9:49
 */
public interface MapLeilei<K,V> {
    //K:就是key值
    //V:就是value值

    /**
     * map集合的大小
     * @return
     */
    int size();
    /**
     * map集合添加
     * @return
     */
    V put(K key, V value);
    /**
     * 使用key查询value
     * @return
     */
    V get(K key);








    /**
     * 存放一条键值对对象
     * @param <K>
     * @param <V>
     */
    interface Entry<K,V> {
        /**
         * 获取我们的key
         * @return
         */
        K getKey();
        /**
         * 获取我们的value
         * @return
         */
        V getValue();
        /**
         * 设置我们的value
         * @return
         */
        V setValue(V value);
    }
}
