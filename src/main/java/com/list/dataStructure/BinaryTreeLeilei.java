package com.list.dataStructure;

/**
 * @author leilei
 * @description: 二叉树
 * @date 2020-01-15 10:10
 */
public class BinaryTreeLeilei {
    //二叉搜索树的特征:添加第一个节点作为平衡值,也就是根节点
    /**
     *节点元素值
     */
    int data;
    /**
     * 左子树
     */
    BinaryTreeLeilei left;
    /**
     * 右子树
     */
    BinaryTreeLeilei right;

    public BinaryTreeLeilei(int data) {
        this.data=data;
    }

    //左边比右边小
    public void insert(BinaryTreeLeilei root,int data){
        if(data > root.data){
            //如果当前的根节点的右边为空的情况下说明第一次添加右子树
            if(root.right==null){
                root.right=new BinaryTreeLeilei(data);
            }else{
                //如果右边有值得情况下,使用递归继续添加
                insert(root.right,data);
            }
        }else {
            //若果当前添加的节点小于根节点的情况下  存放到左边
            if(root.left==null){
                root.left=new BinaryTreeLeilei(data);
            }else{
                insert(root.left,data);
            }
        }
    }

    public void inSearch(BinaryTreeLeilei root){
        if(root !=null){
            inSearch(root.left);
            System.out.println(root.data);
            inSearch(root.right);
        }
    }

    public static void main(String[] args) {
        int data[]={3,6,5,2,1,4};
        BinaryTreeLeilei binaryTreeLeilei = new BinaryTreeLeilei(data[0]);
        for (int i = 1; i < data.length; i++) {
            binaryTreeLeilei.insert(binaryTreeLeilei,data[i]);
        }
        binaryTreeLeilei.inSearch(binaryTreeLeilei);
    }


    /**
     * 二叉搜索树,存在很大的隐患 不能实时找到平衡值  如果第一个值为0 后面值都比0大  导致最后会成为一条线      (以添加的第一个节点作为平衡值)
     *
     * 平衡二叉树 太过于追求平衡  子集是红黑树 相当于对平衡二叉树做了扩展功能  共同特征左旋右旋
     *
     *https://www.cs.usfca.edu/~galles/visualization/Algorithms.html    数据结构地址
     *
     * 红黑树四大特征:
     * 1:根节点一定为黑色
     * 2:不允许两红相连
     * 3:红色节点的两个子节点一定都为黑色
     * 4:节点不是红就是黑
     * 5:默认情况添加的节点为红色节点
     * 备注:如果产生了红色相连  会进行变色和旋转
     * 要么就是改变颜色,要么就是左旋和右旋
     *
     *
     */
}
