package RedBlackTreee;

/**
 * @author leilei
 * @description: 红黑树
 * @date 2020-01-15 17:14
 */
public class RedBlackTreeLeilei {
    //记录当前的根节点
    private Node root;




    class Node{
        /**
         * 节点内容值
         */
        private int value;
        /**
         * 左子树
         */
        private Node left;
        /**
         * 右子树
         */
        private Node right;
        /**
         * 节点颜色
         */
        private NodeColorLeilei color;
        /**
         * 父节点  没父亲节点情况下就是根节点    爷爷节点就是  Node.parent.parent
         */
        private Node parent;
    }

    /**
     * 添加节点
     */
    public void insert(int value){
        if(root==null){
            //说明吗没有根节点,开始封装我们的根节点
            Node newNode = new Node();
            //父节点为空
            newNode.parent=null;
            //根节点一定是黑色
            newNode.color=NodeColorLeilei.black;
            //设置我们的根节点值为value
            newNode.value=value;

            root=newNode;
        }else{
            //有根节点的话  做二叉搜索树
            Node node = insertValue(value);
            //当前节点判断成功时候,开始对我们这个节点实现变色或者旋转  做修复
            //红黑树修复

            repairTree(node);
        }
    }

    private Node insertValue(int value){
       return getPosition(root,value);
    }

    private void repairTree(Node newNode){
        //父亲节点为红色 默认新添加进来的节点也为红色    红红不能存在
        if(newNode.parent.color.equals(NodeColorLeilei.red)){
            //开始实现修复   变色或者旋转
            //获取爷爷节点
            Node g= newNode.parent.parent;
            //获取父亲节点
            Node p=newNode.parent;
            //场景1 父亲节点与当前节点都为红的情况下,并且叔叔节点为黑色或者空的情况下,并且当前节点在右子树的情况下,才能开始左旋
            if(g.left==null||NodeColorLeilei.black.equals(g.left.color) && (newNode.parent.right==newNode)){
                //左旋转
                leftRotate(g);
            }
        }
    }
    private void leftRotate(Node x){
        //x的右子树=Y             红黑树看图:121
       Node y= x.right;
       //左旋成功后 x的右子树就等于y的左子树
       x.right=y.left;
       //改变y的左子树的父亲引用为x
       if(y.left!=null){
           y.left.parent=x;
       }
       if(x.parent==null){
           root=y;
       }else{
           //确定我们的x值在左子树
           if(x.parent.left==x){
               //y上位就在左子树
              x.parent.left=y;
           }else{
               x.parent.right=y;
           }
       }
       //y的左子树就位x
       y.left=x;
       //x的父亲就等于y
       x.parent=y;
    }

    private Node getPosition(Node node,int value){
        if(value>node.value){
            //新的value值大于原来node的值就把新值放在右子树
            if(node.right==null){
                //说明右边第一次赋值
                //说明吗没有根节点,开始封装我们的根节点
                Node newNode = new Node();
                //父节点为空
                newNode.parent=node;
                //当前节点颜色默认情况下红色
                newNode.color=NodeColorLeilei.red;
                //设置我们的根节点值为value
                newNode.value=value;

                //设置父亲的右边 newNode
                node.right=newNode;
                return newNode;
            }else{
                //少递归,栈有深度限制 容易溢出
                return getPosition(node.right,value);
            }

        }else{
            //原来node的值大于新的value值 就把新值放在左子树

            //左子树
            if(node.left==null){
                //说明右边第一次赋值
                //说明吗没有根节点,开始封装我们的根节点
                Node newNode = new Node();
                //父节点为空
                newNode.parent=node;
                //当前节点颜色默认情况下红色
                newNode.color=NodeColorLeilei.red;
                //设置我们的根节点值为value
                newNode.value=value;

                //设置父亲的右边 newNode
                node.left=newNode;
                return newNode;
            }else{
                //少递归,栈有深度限制 容易溢出
                return getPosition(node.left,value);
            }
        }
    }


    public static void main(String[] args) {
        RedBlackTreeLeilei redBlackTreeLeilei = new RedBlackTreeLeilei();
        redBlackTreeLeilei.insert(1);
        redBlackTreeLeilei.insert(2);
        redBlackTreeLeilei.insert(3);
    }
}
