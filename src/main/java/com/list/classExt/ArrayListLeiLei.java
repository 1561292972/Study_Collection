package com.list.classExt;

import com.list.Interface.ListLeilei;

import java.util.Arrays;
import java.util.List;

/**
 * @author leilei
 * @description:
 * @date 2019-12-18 16:56
 */
public class ArrayListLeiLei<E> implements ListLeilei<E> {

    /**
     * transient不被序列化
     */
    transient Object[] elementData;
    /**
     * ArrayList的大小(它包含的元素的数量)。
     */
    private int size;
    /**
     * 默认大小的空数组实例  我们将其与EMPTY_ELEMENTDATA区分开来，以了解在添加第一个元素时需要膨胀多少。
     */
    private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};
    /**
     默认初始容量。
     */
    private static final int DEFAULT_CAPACITY = 10;
    /**
     *要分配的数组的最大大小。一些vm在数组中保留一些标题词。试图分配更大的数组可能会导致inOutOfMemoryError:请求的数组大小超过VM限制
     *Integer 最大值
     */
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;
    /**
     * 构造一个初始容量为空Object数组
     */
    public ArrayListLeiLei() {
        this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
    }

    /**
     * 将指定的元素追加到此列表的末尾。
     * @param e
     * @return
     */
    @Override
    public boolean add(E e) {
        ensureCapacityInternal(size + 1);
        //对我们数组的元素赋值   并对size加加
        elementData[size++] = e;
        return true;
    }

    private void ensureCapacityInternal(int minCapacity) {
        ensureExplicitCapacity(calculateCapacity(elementData, minCapacity));
    }
    private static int calculateCapacity(Object[] elementData, int minCapacity) {
        if (elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
            return Math.max(DEFAULT_CAPACITY, minCapacity);
        }
        return minCapacity;
    }

    private void ensureExplicitCapacity(int minCapacity) {

        // overflow-conscious code   最小容量减去数组长度大于0对数组实现扩容
        if (minCapacity - elementData.length > 0) {
            //对数组实现扩容
            grow(minCapacity);
        }
    }

    /**
     *增加容量，以确保它至少可以容纳 由最小容量参数指定的元素数量。
     * @param minCapacity
     */
    private void grow(int minCapacity) {
        // 获取数组的长度  old 原来的  原来的数组长度
        int oldCapacity = elementData.length;
        //转为二进制的形式可能更好理解，0000 1111(15)右移2位的结果是0000 0011(3)，0001 1010(18)右移3位的结果是0000 0011(3)。
        //新的容量=原来的容量加上原来容量位移1位
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        //新的容量-最小容量小于O
        if (newCapacity - minCapacity < 0){
            //第一次对我们数组做初始化容量操作   只会进去一次
            newCapacity = minCapacity;
        }
        //判断如果我们扩容长度大于Intger (21亿) 最大值的情况下   没见过项目数组会装这么多 都做分页
        if (newCapacity - MAX_ARRAY_SIZE > 0)
            newCapacity = hugeCapacity(minCapacity);
        // 开始对数组实现扩容 对我们数组实现扩容,将我们以前的旧数据,复制到我们的新数组中 返回一个新的数组赋值给elementData
        elementData = Arrays.copyOf(elementData, newCapacity);
    }

    /**
     * 判断我们最小的初始化容量, 如果最小初始化容量大于Integer最大值-8得情况下 直接取Integer最大值 否则取Integer最大值-8
     * @param minCapacity==当前添加的数据下标位置
     * @return  目的 去限制我们数组扩容的最大值  数组最大容量就是Integer.max_value的最大值
     */
    private static int hugeCapacity(int minCapacity) {
        if (minCapacity < 0) // overflow
            throw new OutOfMemoryError();
        return (minCapacity > MAX_ARRAY_SIZE) ?
                Integer.MAX_VALUE :
                MAX_ARRAY_SIZE;
    }









    /**
     * 获取数组数据get() 方法
     * @param index
     * @return
     */
    @Override
    public E get(int index) {
        rangeCheck(index);

        return  elementData(index);
    }

    private void rangeCheck(int index) {
        if (index >= size){
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }
    }
    private String outOfBoundsMsg(int index) {
        return "Index: "+index+", Size: "+size;
    }
    E elementData(int index){
        return (E) elementData[index];
    }













    /**
     * remove() 方法
     * @param index
     * @return
     */
    public E remove(int index) {
        //查看下标是否越界
        rangeCheck(index);
        //根据下标查询在数组中的数据
        E oldValue = elementData(index);
        //计算目的地数组赋值的长度
        int numMoved = size - index - 1;
        //如果长度大于0
        if (numMoved > 0)
            System.arraycopy(elementData, index+1, elementData, index,
                    numMoved);
        elementData[--size] = null; // clear to let GC do its work

        return oldValue;
    }



}
