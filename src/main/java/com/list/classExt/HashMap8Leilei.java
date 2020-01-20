package com.list.classExt;

import com.list.Interface.MapLeilei;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author leilei
 * @description:
 * @date 2020-01-17 10:23
 */
public class HashMap8Leilei<K, V> implements MapLeilei<K, V> {

    /**
     * 默认加载因子0.75
     */
    static final float DEFAULT_LOAD_FACTOR = 0.75f;
    /**
     * 置实际的加载因子
     */
    final float loadFactor;
    /**
     * 就是整个hashmap中存放的所有链表
     */
    transient Node<K,V>[] table;
    /**
     * 阈值 当需要扩容的时候 设置值  为实际hashmap存放大小
     * <p>
     * 容量*加载因子
     */
    int threshold;


    static final int TREEIFY_THRESHOLD = 8;
    /**
     * 最大容量  几十亿吧
     *
     */
    static final int MAXIMUM_CAPACITY = 1 << 30;
    // aka 16  默认数组容量为16
    static final int DEFAULT_INITIAL_CAPACITY = 1 << 4;



    public HashMap8Leilei() {
        this.loadFactor = DEFAULT_LOAD_FACTOR;
    }

    @Override
    public V put(K key, V value) {
        return  putVal(hash(key), key, value, false, true);
    }

    /**
     * 计算key的hash值,如果key为空情况下 存在第一个链表 也就是数组[0]
     *
     * @param key
     * @return
     */
    static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    /**
     *
     * @param hash
     * @param key
     * @param value
     * @param onlyIfAbsent  onlyIfAbsent如果为真,则不改现有值 ,默认false更改
     * @param evict
     * @return
     */
    final V putVal(int hash, K key, V value, boolean onlyIfAbsent,
                   boolean evict) {
        //tab就是数组中存放的所有链表
        Node<K, V>[] tab;
        //单个链表中的某个节点 也就是p = tab[i]就是根绝key计算的index下标 存放的链表
        Node<K, V> p;
        //n就是当前tab数组的长度  i就是index,当前key计算hash值存放在数组的index位置
        int n, i;
        //如果当前hashmap中的table中的数组为空的情况下,就开始扩容
        if ((tab = table) == null || (n = tab.length) == 0) {
            n = (tab = resize()).length;
        }
        //tab[i = (n - 1) & hash] 里面的i就是index,当前key计算hash值存放在数组的index位置
        //如果从数组中没有获取到对应的链表 就开始第一次进行赋值
        if ((p = tab[i = (n - 1) & hash]) == null) {
            tab[i] = newNode(hash, key, value, null);
        }else{
            //else 说明当前的key产生index冲突了
            //e就是上面的p
            Node<K,V> e;
            K k;
            //如果hash值相等的情况下并且key的内容也相等 就实现对我们的key对应的value覆盖
            if (p.hash == hash &&
                    ((k = p.key) == key || (key != null && key.equals(k)))) {
                e = p;
            }/*else if (p instanceof TreeNode) {
                //若果当前的node也就是p是红黑树的类型 就以红黑树的类型存放
                e = ((TreeNode<K, V>) p).putTreeVal(this, tab, hash, key, value);
            }*/else{
                //binCount作用:可以继续循环  靠break停止循环
                //不是红黑树存放的 就开始遍历链表   ++binCount第一次时候执行循环为0 第二次的时候加加
                for (int binCount = 0; ; ++binCount) {
                    //一直遍历遍历到最后一个节点因为最后一个节点它没有下一个节点
                    if ((e = p.next) == null) {
                        //说明 hash值相同,值不同
                        //链表下一个节点为当前的key //jdk7新插入的放在最前面 头插法  这个地方8是尾插法
                        p.next = newNode(hash, key, value, null);
                        //0-7
                        if (binCount >= TREEIFY_THRESHOLD - 1) {
                            treeifyBin(tab, hash);
                            break;
                        }
                    }
                    //走遍历链表的后面如果hash值相等的情况下并且key的内容也相等 就实现对我们的key对应的value覆盖  但这里走的是链表里的
                    if (e.hash == hash &&
                            ((k = e.key) == key || (key != null && key.equals(k)))) {
                        break;
                    }
                    p = e;
                }
            }
            //插入相同的key  进行修改值
            if (e != null) { // existing mapping for key
                V oldValue = e.value;
                //如果原来值为null的情况下就修改
                if (!onlyIfAbsent || oldValue == null)
                    e.value = value;
                afterNodeAccess(e);
                return oldValue;
            }
        }
        return null;
    }

    void afterNodeAccess(Node<K, V> e) {

    }
    final Node<K,V>[] resize() {
        //获取原来table的数组
        Node<K,V>[] oldTab = table;
        //若果原来数组为空的话 长度就设置为0 否则就设置他的长度
        int oldCap = (oldTab == null) ? 0 : oldTab.length;
        //获取原来的加载因子阈值容量大小 16*0.75
        int oldThr = threshold;
        /**
         * newCap:新的容量    newThr:新的加载因子的阈值
         */
        int newCap, newThr = 0;
        //原来大小大于0的情况
        if (oldCap > 0) {
            //如果原来容量>最大容量限制的情况下
            if (oldCap >= MAXIMUM_CAPACITY) {
                //加载因子阈值就是Interger的最大值
                threshold = Integer.MAX_VALUE;
                return oldTab;
            }//否则新的容量为原来的容量*2<Interger的最大值    并且原来的容量>默认16容量情况下
            else if ((newCap = oldCap << 1) < MAXIMUM_CAPACITY &&
                    oldCap >= DEFAULT_INITIAL_CAPACITY) {
                //新的加载因子阈值等于原来加载因子阈值乘以2  // double threshold
                newThr = oldThr << 1;
            }//原来加载因子阈值>0的情况下
            else if (oldThr > 0) {
                //新的容量为原来加载因子阈值
                newCap = oldThr;
            }
            else {
                //新的容量为16
                newCap = DEFAULT_INITIAL_CAPACITY;
                //新的加载因子阈值为16*0.75  第一次
                newThr = (int)(DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY);
            }
            //若果新的加载因子值为0的情况下
            if (newThr == 0) {
                //ft=新的容量*阈值(0.75)
                float ft = (float)newCap * loadFactor;
                //新的加载因子阈值=
                newThr = (newCap < MAXIMUM_CAPACITY && ft < (float)MAXIMUM_CAPACITY ?
                        (int)ft : Integer.MAX_VALUE);
            }
            //加载因子阈值赋值为新的
            threshold = newThr;
            //新的table=16 赋值给老的table
            Node<K,V>[] newTab = (Node<K,V>[])new Node[newCap];
            table = newTab;
            return newTab;
        }
        return null;
    }
    /**
     * 创建node
     * @param hash
     * @param key
     * @param value
     * @param next
     * @return
     */
    Node<K,V> newNode(int hash, K key, V value, Node<K,V> next) {
        return new Node<>(hash, key, value, next);
    }

    /**
     * 转红黑树进行存放
     */
    final void treeifyBin(Node<K,V>[] tab, int hash) {
/*        int n, index; Node<K,V> e;
        if (tab == null || (n = tab.length) < MIN_TREEIFY_CAPACITY)
            resize();
        else if ((e = tab[index = (n - 1) & hash]) != null) {
            TreeNode<K,V> hd = null, tl = null;
            do {
                TreeNode<K,V> p = replacementTreeNode(e, null);
                if (tl == null)
                    hd = p;
                else {
                    p.prev = tl;
                    tl.next = p;
                }
                tl = p;
            } while ((e = e.next) != null);
            if ((tab[index] = hd) != null)
                hd.treeify(tab);
        }*/
    }











    /**
     * hashMap8的底层采用单向链表
     * @param <K>
     * @param <V>
     */
    static class Node<K,V>{
        /**
         * hash值
         */
        final int hash;
        //key值
        final K key;
        /**
         * value值
         */
        V value;
        /***
         * 下一个节点
         */
        Node<K,V> next;

        Node(int hash, K key, V value, Node<K,V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }

        public final K getKey()        { return key; }
        public final V getValue()      { return value; }
        @Override
        public final String toString() { return key + "=" + value; }


        public final V setValue(V newValue) {
            V oldValue = value;
            value = newValue;
            return oldValue;
        }


    }


























    @Override
    public int size() {
        return 0;
    }


    @Override
    public V get(K key) {
        return null;
    }
}
