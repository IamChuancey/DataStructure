package Tree.RBTree;

public class RBTree {
    private static final boolean RED=true;
    private static final boolean BLACK=false;
    private RBTNode root;

    private boolean isRed(RBTNode x){
        if(x==null) return false;
        return x.color==RED;
    }

    //左旋操作
    private RBTNode rotateLeft(RBTNode h){
        RBTNode x=h.right;
        h.right=x.left;
        x.left=h;
        x.color=h.color;
        h.color=RED;
        return x;
    }

    //右旋操作
    private RBTNode rotateRight(RBTNode h){
        RBTNode x=h.left;
        h.left=x.right;
        x.right=h;
        x.color=h.color;
        h.color=RED;
        return x;
    }

    //变色操作,变色操作在插入和删除的作用是相反的。
    /*
       flipColors在插入时，使节点h，left，right来结合的4-节点变成1个3-节点(h节点)和两个2-节点(left,right)
       flipColors在删除时，使节点h，left，right结合成4-节点。
     */
    private void flipColors(RBTNode h){
        h.color=!h.color;
        h.left.color=!h.left.color;
        h.right.color=!h.right.color;
    }

    //插入新节点
    public void  put(int value){
       root=put(root,value);
       root.color=BLACK;
    }

    //向以节点h为根节点的红黑树中插入新节点，插入后返回新的树的根节点
    private RBTNode put(RBTNode h,int value){
        if(h==null) return new RBTNode(value, RED);
        if(value<h.value){
            h.left=put(h.left, value);
        }else if(value>h.value){
            h.right=put(h.right, value);
        }
        //第一种情况左旋
        if(isRed(h.right)&&!isRed(h.left)){
            return rotateLeft(h);
        }
        //第二种情况右旋
        if(isRed(h.left)&&isRed(h.left.left)){
            return rotateRight(h);
        }
        //第三种情况变色
        if(isRed(h.left)&&isRed(h.right)){
            flipColors(h);
        }
        return h;
    }

    //处理2-节点
    /**
     * 从兄弟节点将本节点变换为非2-节点(左节点)
     */
    private RBTNode moveRedLeft(RBTNode h){
        flipColors(h); //其兄弟节点为2节点,直接颜色变换来生成4节点
        if(isRed(h.right.left)){ //兄弟节点为非2-节点,从兄弟节点中借一个值，使得该节点为3节点
            h.right=rotateRight(h.right);
            h = rotateLeft(h);
            flipColors(h);
        }
        return h;
    }

    //处理2-节点
    /**
     * 从兄弟节点将本节点变换为非2-节点(右节点)
     */
    private  RBTNode moveRedRight(RBTNode h){
        flipColors(h); //其兄弟节点为2节点,直接颜色变换来生成4节点
        if (isRed(h.left.left)){ //兄弟节点为非2-节点,从兄弟节点中借一个值，使得该节点为3节点
            h=rotateRight(h);
            flipColors(h);
        }
        return h;
    }

    //沿着左链接删除最小节点
    public void deleteMin(){
        if(!isRed(root.left) && !isRed(root.right)){ // 如果根节点的左右子节点是2-节点，我们可以将根设为红节点，这样才能进行后面的moveRedLeft操作
            root.color=RED;
        }
        root=deleteMin(root);
        if (root!=null){  // 删除完毕以后，我们将根节点的颜色复原
            root.color = BLACK;
        }
    }

    private RBTNode deleteMin(RBTNode h){
        if(h.left==null) return  null;
        //意味着h的左子节点为一个2节点，因此需要将其变为3-节点或者临时的4-节点
        if(!isRed(h.left)&&!isRed(h.left.left)){
            h=moveRedLeft(h);
        }
        //删除最小左节点
        h.left=deleteMin(h.left);
        //返回的时候再次解开临时的4节点，使整个树平衡
        return balance(h);
    }

    //沿着右链接删除最大节点
    public void deleteMax(){
        if(!isRed(root.left) && !isRed(root.right)){ // 如果根节点的左右子节点是2-节点，我们可以将根设为红节点，这样才能进行后面的moveRedRight操作
            root.color=RED;
        }
        root=deleteMax(root);
        if (root!=null){  // 删除完毕以后，我们将根节点的颜色复原
            root.color = BLACK;
        }
    }

    private RBTNode deleteMax(RBTNode h){
        if(h.right==null) return  null;
        if(isRed(h.left))
            h = rotateRight(h);
        if(!isRed(h.right)&&!isRed(h.right.left))
            h = moveRedRight(h);
        h.right = deleteMax(h.right);
        return balance(h);
    }

    //balance函数就是在返回的时候由下到上分解临时的4节点，使整个树再次平衡
    private RBTNode balance(RBTNode h){
        if (isRed(h.right)) h = rotateLeft(h);
        if (isRed(h.left) && isRed(h.left.left)) h=rotateRight(h);
        if (isRed(h.left) && isRed(h.right))    flipColors(h);
        return h;
    }

    //删除节点
    public void delete(int value){
        // 如果根节点的左右子节点是2-节点，我们可以将根设为红节点，这样才能进行后面的moveRedLeft操作，因为左子要从根节点借一个
        if (!isRed(root.right) && !isRed(root.left))
            root.color = RED;
        root = delete(root,value);
        if (root!=null){
            root.color = BLACK; //删除完毕后，我们将根节点的颜色复原
        }
    }

    //delete函数其实是deleteMin和deleMax的一种组合
    private RBTNode delete(RBTNode h,int value){
        // 当目标键小于当前键的时候，沿着左链接移动，向左搜寻
        if(value<h.value){
            if (!isRed(h.left) && !isRed(h.left.left)){
                h= moveRedLeft(h);
            }
            h.left = delete(h.left, value);
        }else {
            // 当目标键大于当前值的时候，沿着右链接移动，向右搜寻
            if (isRed(h.left))
                h = rotateRight(h);
            //无后继节点时，即待删除节点本身为叶子节点，这时直接删除
            if (h.right == null && value == h.value){
                System.out.println("There!");
                return null;
            }
            if (!isRed(h.right) && !isRed(h.right.left))
                h = moveRedRight(h);
            if (value == h.value) { //找到待删节点，将其和后继节点进行交换, 然后删除交换后的后继节点
                System.out.println("Here!");
                RBTNode x = min(h.right);
                h.value = x.value;
                h.right = deleteMin(h.right);
            }else {
                h.right = delete(h.right, value);
            }
        }
        return balance(h);
    }


    public RBTNode min(RBTNode x){
        while (x.left!=null){
            x=x.left;
        }
        return x;
    }

    public void preOrder(RBTNode root){
        if(root!=null){
            System.out.println(root.value);
            preOrder(root.left);
            preOrder(root.right);
        }
    }

    public static void main(String[] args) {
        RBTree tree=new RBTree();
        tree.put(1);
        tree.put(2);
        tree.put(3);
        tree.put(4);
        tree.put(5);
        tree.preOrder(tree.root);
        System.out.println("After delete");
        tree.delete(3);
        tree.preOrder(tree.root);
    }
}
