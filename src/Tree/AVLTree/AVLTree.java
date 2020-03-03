package Tree.AVLTree;

public class AVLTree {
    //获取某一节点的高度
    public int getHeight(AVLNode node){
        if(node==null){
            return 0;
        }
        return node.height;
    }
    //获取节点的平衡因子
    public int getBalanceFactor(AVLNode node){
        if(node==null){
            return 0;
        }
        return getHeight(node.left)-getHeight(node.right);
    }

    //判断以node为跟节点的树是否平衡
    public boolean isBalanced(AVLNode node){
        if(node==null){
            return true;
        }
        int balancedFactor=Math.abs(getBalanceFactor(node));
        if(balancedFactor>1){
            return false;
        }
        return isBalanced(node.left)&&isBalanced(node.right);
    }

    //1. LL情况下进行右旋操作
    public AVLNode rightRotate(AVLNode node){
       AVLNode x=node.left;
       AVLNode t3=x.right;
       x.right=node;
       node.left=t3;
       //更新node节点和x节点的height，这对平衡操作非常重要
       node.height=Math.max(getHeight(node.left), getHeight(node.right))+1;
       x.height=Math.max(getHeight(x.left), getHeight(x.right))+1;
       return x;
    }

    //2. RR情况下进行左旋操作
    public AVLNode leftRotate(AVLNode node){
       AVLNode x=node.right;
       AVLNode t3=x.left;
       x.left=node;
       node.right=t3;
       //更新node节点和x节点的height，这对平衡操作非常重要
       node.height=Math.max(getHeight(node.left), getHeight(node.right))+1;
       x.height=Math.max(getHeight(x.left), getHeight(x.right))+1;
       return x;
    }

    //3. LR情况下先对node的左子树进行左旋操作，再对node进行右旋操作

    //4. RL情况下先对node的右子树进行右旋操作，再对node进行左旋操作

    // 向以node为根的二分搜索树中插入新元素val，递归算法
    //返回插入新节点后二分搜索树的根节点
    //添加节点时，会导致二叉搜索树失衡，因此需要进行左右旋操作
    //平衡二叉搜索树时，首先从不平衡的子树开始平衡，然后再一步步扩展到整个树
    public AVLNode add(AVLNode node, int val){
        //递归算法插入新元素
        if(node==null){
            return new AVLNode(val);
        }
        if(node.value>val){
            node.left=add(node.left,val);
        }else if(node.value<val){
            node.right=add(node.right, val);
        }
        //更新height
        node.height=1+Math.max(getHeight(node.left), getHeight(node.right));
        //计算平衡因子
        int balanceFactor=getBalanceFactor(node);
        //LL情况右旋
        if(balanceFactor>1&&getBalanceFactor(node.left)>=0){
            return rightRotate(node);
        }
        //RR情况左旋
        if(balanceFactor<-1&&getBalanceFactor(node.right)<=0){
            return leftRotate(node);
        }
        //LR情况先对其左子树左旋，再对其右旋
        if(balanceFactor>1&&getBalanceFactor(node.left) < 0){
            node.left=leftRotate(node.left);
            return rightRotate(node);
        }
        //RL情况先对其右子树右旋，再对其左旋
        if(balanceFactor<-1&&getBalanceFactor(node.right) > 0){
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }
        return node;
    }


    //向以node为根节点的二叉搜索树中删除元素val,
    //删除元素时，首先要调整树结构使得其仍旧是一个二叉搜索树
    //其次，要保证其是平衡的二叉搜索树
    public AVLNode remove(AVLNode node, int val){
        //递归算法找到待删除节点
        if(node==null){
            return null;
        }
        AVLNode retNode; //待删除节点
        if(val<node.value){
            node.left=remove(node.left, val);
            retNode=node;
        }
        else if(val>node.value){
            node.right=remove(node.right, val);
            retNode=node;
        }else{
            //val=node.value 即找到待删除节点
            /*
               先对二叉搜索树做删除操作
             */
            //待删除节点左子树为空的情况
            if(node.left==null){
                AVLNode rightNode=node.right;
                node.right=null;
                retNode=rightNode;
            }
            //待删除节点右子树为空的情况
            else if(node.right==null){
                AVLNode leftNode=node.left;
                node.left=null;
                retNode=leftNode;
            }else {
                //待删除节点左右子树均不为空的情况
                // 找到比待删除节点大的最小节点, 即待删除节点右子树的最小节点，此节点即是待删除节点的后继节点
                // 用这个节点顶替待删除节点的位置
                AVLNode successor = minimum(node.right);
                successor.right = remove(node.right, successor.value);
                successor.left = node.left;
                node.left = node.right = null;
                node = successor;
                retNode=successor;
            }
           System.out.println("待删除："+node.value);
        }
        if(retNode==null){
            return null;
        }
        //下面开始维护树的平衡
        //更新height
        retNode.height = 1+Math.max(getHeight(retNode.left),getHeight(retNode.right));
        //计算平衡因子
        int balanceFactor = getBalanceFactor(retNode);
        if(balanceFactor > 1 && getBalanceFactor(retNode.left)>=0) {
            //右旋LL
            return rightRotate(retNode);
        }
        if(balanceFactor < -1 && getBalanceFactor(retNode.right)<=0) {
            //左旋RR
            return leftRotate(retNode);
        }
        //LR
        if(balanceFactor > 1 && getBalanceFactor(retNode.left) < 0){
            node.left = leftRotate(retNode.left);
            return rightRotate(retNode);
        }
        //RL
        if(balanceFactor < -1 && getBalanceFactor(retNode.right) > 0){
            node.right = rightRotate(retNode.right);
            return leftRotate(retNode);
        }
        return node;
    }


    public AVLNode minimum(AVLNode node){
        if(node.left == null){
            return node;
        }
        return minimum(node.left);
    }

    public void inOrder(AVLNode root){
        if(root!=null){
            inOrder(root.left);
            System.out.println(root.value);
            inOrder(root.right);
        }
    }

    public void preOrder(AVLNode root){
        if(root!=null){
            System.out.println(root.value);
            preOrder(root.left);
            preOrder(root.right);
        }
    }

    public static void main(String[] args) {
        //测试add函数
//        Tree.AVLTree.AVLNode root=new Tree.AVLTree.AVLNode(1);
//        Tree.AVLTree.AVLTree tree=new Tree.AVLTree.AVLTree();
//        Tree.AVLTree.AVLNode root1=tree.add(root, 2);
//        tree.preOrder(root1);
//        Tree.AVLTree.AVLNode root2=tree.add(root, 3);
//        System.out.println("root value: "+root2.value);
//        tree.preOrder(root2);
//        Tree.AVLTree.AVLNode root3=tree.add(root2, 4);
//        System.out.println("root value: "+root3.value);
//        tree.preOrder(root3);
//        Tree.AVLTree.AVLNode root4=tree.add(root3, 5);
//        System.out.println("root value: "+root4.value);
//        tree.preOrder(root4);
//        Tree.AVLTree.AVLNode root5=tree.add(root4, 6);
//        System.out.println("root value: "+root5.value);
//        tree.preOrder(root5);
//        Tree.AVLTree.AVLNode root6=tree.add(root5,7);
//        System.out.println("root value: "+root6.value);
//        tree.preOrder(root6);

          //测试delete函数 必须使用add函数节点
          AVLNode node=new AVLNode(4);
//          Tree.AVLTree.AVLNode node1=new Tree.AVLTree.AVLNode(2);
//          Tree.AVLTree.AVLNode node2=new Tree.AVLTree.AVLNode(6);
//          Tree.AVLTree.AVLNode node3=new Tree.AVLTree.AVLNode(1);
//          Tree.AVLTree.AVLNode node4=new Tree.AVLTree.AVLNode(8);
//          Tree.AVLTree.AVLNode node5=new Tree.AVLTree.AVLNode(7);
//          Tree.AVLTree.AVLNode node6=new Tree.AVLTree.AVLNode(5);
          AVLTree tree=new AVLTree();
          AVLNode root=tree.add(node,2);
          AVLNode root1=tree.add(root, 1);
          AVLNode root2=tree.add(root1, 7);
          AVLNode root3=tree.add(root2, 6);
          AVLNode root4=tree.add(root3,5);
          AVLNode root5=tree.add(root4, 8);
          tree.inOrder(root5);
          AVLNode rootA=tree.remove(node, 5);
          System.out.println("After remove: ");
          tree.inOrder(rootA);
    }
}
