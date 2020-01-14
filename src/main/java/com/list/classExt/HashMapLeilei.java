package com.list.classExt;

import com.list.Interface.MapLeilei;


/**
 * @author leilei
 * @description:
 * @date 2019-12-31 16:42
 */
public class HashMapLeilei<K, V> implements MapLeilei<K, V> {

    //默认初始容量位16 2的4次方
    static final int DEFAULT_INITIAL_CAPACITY = 1 << 4; // aka 16
    /**
     * 加载因子为0.75f 作用对我们数组实现扩容
     */
    static final float DEFAULT_LOAD_FACTOR = 0.75f;
    /**
     * 最大初始化容量 10 7374 1824  10个亿
     */
    static final int MAXIMUM_CAPACITY = 1 << 30;
    /**
     * 设置实际的加载因子
     */
    final float loadFactor;
    /**
     * 阈值 当需要扩容的时候 设置值  为实际hashmap存放大小
     * <p>
     * 容量*加载因子
     */
    int threshold;
    /**
     * hashmap 底层数组存放为空
     */
    transient Entry<K, V>[] tables = null;
    /**
     * 集合的大小
     */
    transient int size;


    /**
     * 默认hashmap初始容量 16  加载因子0.75
     * <p>
     * 每个链表存放的可能是hash冲突或者计算的index冲突
     */
    public HashMapLeilei() {
        this(DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    /**
     * @param initialCapacity 初始容量
     * @param loadFactor      加载因子(目的,扩容用)
     */
    public HashMapLeilei(int initialCapacity, float loadFactor) {
        //检查手动设置的初始容量是否合理
        if (initialCapacity < 0)
            throw new IllegalArgumentException("Illegal initial capacity: " +
                    initialCapacity);
        //检查手动设置的初始容量大于MAXIMUM_CAPACITY 定义的最大初始化容量    最大容量限制为定义的最大初始化容量
        if (initialCapacity > MAXIMUM_CAPACITY)
            initialCapacity = MAXIMUM_CAPACITY;
        //检查手动设置的加载因子是否合理
        if (loadFactor <= 0 || Float.isNaN(loadFactor))
            throw new IllegalArgumentException("Illegal load factor: " +
                    loadFactor);
        //设置加载因子和实际的hashmap存放的阈值
        this.loadFactor = loadFactor;
        threshold = initialCapacity;
        init();
    }

    //定义空方法给子类实现扩展功能
    void init() {

    }


    @Override
    public V put(K key, V value) {
        //如果tables 为空的情况 开始初始化我们的数组大小
        if (tables == null) {
            inflateTable(threshold);
        }
        //支持存放null的key
        if(key==null){
            return putForNullKey(key,value);
        }
        //计算hash值
        int hash = hash(key);
        //根据hash值计算   数组下标存放的位置
        int index = indexFor(hash, tables.length);
        //最新hashCode碰撞的值在链表前面
        //判断hashcode相同且对象值也相同的情况下,修改value值
        for (Entry e = tables[index]; e != null; e = e.next) {
            //table[index] 去除相同的hashCode 但是value不同
            Object k;
            ////判断hashcode相同且对象值也相同的情况下,修改value值
            if (e.hash == hash && ((k = e.getKey()) == key || e.getKey().equals(key))) {
                //获取原来的value
                Object oldValue = e.getValue();
                //设置最新的值
                e.setValue(value);
                return (V) oldValue;
            }
        }

        //否则情况下 添加元素
        addEntry(hash, key, value, index);
        return null;
    }

    private V putForNullKey(K key, V value) {
        //检查key为null是否存在,如果存在的情况修改对应的值
        for (Entry<K,V> e = tables[0]; e != null; e = e.next) {
            if (e.getKey()==null) {
                //获取原来的value
                V oldValue = e.getValue();
                //设置最新的值
                e.setValue(value);
                return (V) oldValue;
            }
        }
        //不存在  存放到数组O的位置  第一个链表
        addEntry(0,null,value,0);
        return null;
    }

    /**
     * 默认数组的初始化
     *
     * @param toSize
     */
    private void inflateTable(int toSize) {

        int capacity = roundUpToPowerOf2(toSize);
        //计算初始容量 capacity16*0.75=12,MAX..=10亿  Math.min取最小   threshold才是真正hashmap存放大小
        threshold = (int) Math.min(capacity * loadFactor, MAXIMUM_CAPACITY + 1);
        //对数组中的容量进行初始化
        tables = new Entry[capacity];
    }

    private static int roundUpToPowerOf2(int number) {
        // assert number >= 0 : "number must be non-negative";
        return number >= MAXIMUM_CAPACITY
                ? MAXIMUM_CAPACITY
                : (number > 1) ? Integer.highestOneBit((number - 1) << 1) : 1;
    }

    //计算hash值
    final int hash(Object k) {
        int h = 0;
     /*   if (0 != h && k instanceof String) {
            sun.misc.Hashing.stringHash32((String)k)
            return sun.misc.h;
        }
*/
        h ^= k.hashCode();
        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }

    //根据hash值计算index下标位置
    static int indexFor(int h, int length) {
        return h & length - 1;
    }

    /**
     * @param hash
     * @param key
     * @param value
     * @param bucketIndex 桶 索引下标
     */
    void addEntry(int hash, K key, V value, int bucketIndex) {
        //如果我们size>阈值(12) && 查找我们index 冲突的情况下才开始扩容
        if((size>=threshold) &&(null!=tables[bucketIndex])){
            //为什么要加&& 假设size=12 容量现在为16 还没用到16的时候就开始扩容了   然后假设tables[0]没有存任何元素
            // 已经存的12个元素可能都均摊到下标为1-13  正好插入的index=0这个时候就没有产生index冲突问题 因为index=0的没有元素,为null 容量又够了,这个时候不需要扩容 避免浪费资源
            //最终目的保证每个数组都有存放的元素
            //新的数组的长度为原来的 tables长度*2 16*2=32
            resize(2*tables.length);
            //
            hash=(null!=key)?hash(key):0;
            //计算当前新增的key的index
            bucketIndex=indexFor(hash,tables.length);
        }
        createEntry(hash, key, value, bucketIndex);
    }

    void resize(int newCapacity) {
        // 获取原来的table
        Entry[] oldTable = tables;
        // 获取原来旧的tables数组长度
        int oldCapacity = oldTable.length;
        // 如果我们原来数组长度等于数组最大限制的容量的话 阈值为Integer最大值
        if (oldCapacity == MAXIMUM_CAPACITY) {
            threshold = Integer.MAX_VALUE;
            return;
        }
        // 创建我们的新的容量为32  之前数组为16 现在为32 会存在哪些问题?  在计算index下标的时候是根据数组长度和hash计算的
        // 现在数组长度变了  所以每次扩容的时候需要重新计算index值 赋值到新的tables中
        Entry[] newTable = new Entry[newCapacity];
        // 重新计算我原来tablekey中的index在新的table 下标位置s
        transfer(newTable, false);
        tables = newTable;
        //新的扩容的阈值为多少 新的容量  32*0.75=24  下次再24的时候就要开始扩容了
        threshold = (int) Math.min(newCapacity * loadFactor, MAXIMUM_CAPACITY + 1);
    }
    /**
     * 重新计算index下标位置
     *
     * @param newTable
     * @param b
     */
    private void transfer(Entry[] newTable, boolean b) {
        // 获取新的tables的长度
        int newCapacity = newTable.length;
        // 遍历原来tables所有的链表
        for (Entry<K, V> e : tables) {
            // 遍历单个链表
            while (null != e) {
                // 获取下一个next
                Entry<K, V> next = e.next;
                // 计算原来key的index 在新的table中位置
                int index = indexFor(e.hash, newCapacity);
                e.next = newTable[index];
                newTable[index] = e;
                // 继续遍历下一个节点
                e = next;
            }
        }
    }

    private void createEntry(int hash, K key, V value, int bucketIndex) {
        //获取原来的Entry对象     如果是空的情况,就是没有发生hash冲突
        Entry<K, V> next = tables[bucketIndex];
        //new 最新添加的entry 的next下一个就是原来的Entry对象
        tables[bucketIndex] = new Entry<>(hash, key, value, next);
    }





























    @Override
    public int size() {
        return 0;
    }


    @Override
    public V get(K key) {
        //支持存放null的key
        if(key==null){
            return getForNullKey();
        }
        //计算hash值
        int hash = hash(key);
        //根据hash值计算   数组下标存放的位置
        int index = indexFor(hash, tables.length);
        //查找我们的key存放在那个链表中,遍历我们的链表查找key是否相同
        for (Entry e = tables[index]; e != null; e = e.next) {
            Object k;
            ////判断hashcode相同且对象值也相同的情况下,修改value值
            if (e.hash == hash && ((k = e.getKey()) == key || e.getKey().equals(key))) {
                return (V) e.getValue();
            }
        }
        return null;
    }

    private V getForNullKey() {
        //查找数组O的下标 第一个链表
        for (Entry e = tables[0]; e != null; e = e.next) {
            if(e.getKey()==null){
                return (V) e.getValue();
            }
        }
        return null;
    }


    //Entry就是HashMap中的Node
    class Entry<K, V> implements MapLeilei.Entry<K, V> {
        /**
         * Key
         */
        private K k;
        /**
         * value
         */
        private V v;
        //next 指向下一个Entry对象
        private Entry<K, V> next;

        //存放没个entry对象的hash值
        private int hash;


        public Entry(int hash, K key, V value, Entry<K, V> next) {
            this.hash = hash;
            this.k = key;
            this.v = value;
            this.next = next;
            size++;
        }


        /**
         * 使用有参构造函数赋值
         *
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
            this.v = value;
            return v;
        }
    }
}
