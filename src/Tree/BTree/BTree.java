package Tree.BTree;

public class BTree<K extends Comparable<K>,V> {
    private int Order=6; //B树的阶，B树的"叉"
    private BTNode mRoot; //B树的根节点
    private int size; //B树的键-值对的数量

    public BTree(int order){
        this.Order=order;
    }

    private class BTNode<K extends Comparable<K>,V> {
        private int LOWER_BOUND_KEYNUM=(int) (Math.ceil(Order/2)-1);
        private int UPPER_BOUND_KEYNUM=Order-1;

        protected boolean misLeaf; //标记此节点是否为叶子节点
        protected int mCurrentKeyNum;//当前节点的键数量计数器
        protected BTKeyValue<K,V>mKeyValues[];//用于存储键-值对的数组
        protected BTNode<K,V> mChildren[];//用于存储子节点的数组

        public BTNode(){
            misLeaf=true;
            mCurrentKeyNum=0;
            mKeyValues=new BTKeyValue[UPPER_BOUND_KEYNUM];
            mChildren=new BTNode[UPPER_BOUND_KEYNUM+1];
        }

        protected BTNode<K,V>getChildNodeAtIndex(BTNode<K, V> btNode, int keyIdx, int nDirection){
            if(btNode.misLeaf){
                return null;
            }
            keyIdx+=nDirection;
            if(keyIdx<0|| keyIdx>btNode.mCurrentKeyNum){
                throw  new IllegalArgumentException();
            }
            return btNode.mChildren[keyIdx];
        }

        /*
         判断父节点的keyIdx位上的子节点是否存在左兄弟节点
         */
        protected boolean hasLeftSiblingAtIndex(BTNode<K,V>parentNode,int keyIdx){
            if(keyIdx-1>0&&parentNode.mChildren[keyIdx-1]!=null){
                return true;
            }else return false;
        }

        /*
        判断父节点的keyIdx位上的子节点是否存在右兄弟节点
         */
        protected boolean hasRightSiblingAtIndex(BTNode<K,V>parentNode,int keyIdx){
            if(keyIdx<parentNode.mCurrentKeyNum&&parentNode.mChildren[keyIdx+1]!=null){
                return true;
            }else return false;
        }

        /*
         返回btNode节点中位于keyIdx左边的兄弟节点
         */
        protected BTNode<K,V>getLeftSiblingAtIndex(BTNode<K,V>btNode,int keyIdx){
            return getChildNodeAtIndex(btNode,keyIdx,-1);
        }

        /*
         返回btNode节点中位于keyIndex右边的兄弟节点
         */
        protected BTNode<K,V>getRightSiblingAtIndex(BTNode<K,V>btNode,int keyIdx){
            return getChildNodeAtIndex(btNode,keyIdx,1);
        }

    }

    /*
      查找指定键所对应的值
     */
     public V search(K key){
         BTNode<K,V>currentNode=mRoot;
         while (currentNode!=null){
             int possibleIndex=binarySearch(mRoot,key);
             BTKeyValue<K,V>possibleKeyValue=currentNode.mKeyValues[possibleIndex];
             // 判断二分查找返回位置索引处的元素是否为查找的元素，若是则返回其值，如不是，则迭代到下一个可能的结点中查找
             if (possibleIndex < currentNode.mCurrentKeyNum && key.compareTo(possibleKeyValue.mKey) == 0) {
                 return possibleKeyValue.mValue;
             } else {
                 currentNode = currentNode.mChildren[possibleIndex];
             }
         }
         return null;
     }


    //  二分查找法：查找当前节点中键的位置，若找到键，则返回键的位置；若没找到，则返回键应该插入的位置
    private int binarySearch(BTNode<K,V>btNode,K key){
        BTKeyValue<K, V>[] keys = btNode.mKeyValues;
        int lo = 0;
        int hi = btNode.mCurrentKeyNum - 1;
        while (lo <= hi) {
            int mid = (hi - lo) / 2 + lo;
            int cmp = key.compareTo(keys[mid].mKey);
            if (cmp == 0) {
                return mid;
            } else if (cmp > 0) {
                lo = mid + 1;
            } else if (cmp < 0) {
                hi = mid - 1;
            }
        }
        return lo;
    }

    /*
       插入节点
     */
    public void add(K key,V value){
       if(mRoot==null){
           mRoot=createNode();
       }
       //使用递归方法将键-值对插入到BTree结构中
       mRoot=insert(mRoot,key,value);
    }


   // 当一个节点的键值达到节点的上限时，需要拆分节点，并将节点的中间键-值上升到父节点
    private BTNode<K,V>split(BTNode<K,V>x){
        int mid=x.mCurrentKeyNum/2;
        BTNode<K,V>left=new BTNode<>();
        for(int i=0;i<mid;i++){
            left.mKeyValues[i]=x.mKeyValues[i];
            left.mChildren[i]=x.mChildren[i];
        }
        left.mChildren[mid]=x.mChildren[mid];
        left.misLeaf=x.misLeaf;
        left.mCurrentKeyNum=mid;

        BTNode<K,V>right=new BTNode<>();
        for(int i=mid+1;i<x.mCurrentKeyNum;i++){
            right.mKeyValues[i-mid-1]=x.mKeyValues[i];
            right.mChildren[i-mid-1]=x.mChildren[i];
        }
        right.mChildren[x.mCurrentKeyNum-mid-1]=x.mChildren[x.mCurrentKeyNum];
        right.misLeaf=x.misLeaf;
        right.mCurrentKeyNum=x.mCurrentKeyNum-mid-1;

        BTNode<K,V>parent=new BTNode<>();
        parent.mCurrentKeyNum=1;
        parent.misLeaf=false;
        parent.mKeyValues[0]=x.mKeyValues[mid];
        parent.mChildren[0]=left;
        parent.mChildren[1]=right;
        return parent;
    }


    private BTNode<K,V> insert(BTNode<K,V>x,K key,V value){
        //1.首先判断当前插入的节点是否已经为满，若满，则将此节点分裂
        if(x.mCurrentKeyNum==x.UPPER_BOUND_KEYNUM){
            x=split(x);
        }
        //2.对没有满的结点进行键-值对的查找，如果该键已存在，则更新值；如果不存在，则插入新节点
        int possibleIdx=binarySearch(x,key);
        BTKeyValue<K,V>possibleKeyValue=x.mKeyValues[possibleIdx];
        //2.1 该键已存在，则更新值
        if(possibleKeyValue!=null&&key.compareTo(possibleKeyValue.mKey)==0){
            possibleKeyValue.mValue=value;
            return x;
        }
        //2.2 否则，则插入新节点
        /*
          如果当前节点是叶子节点，直接插入
         */
        if(x.misLeaf){
           for(int i=x.mCurrentKeyNum-1;i>possibleIdx;i--){
               x.mKeyValues[i]=x.mKeyValues[i-1];
           }
           x.mKeyValues[possibleIdx]=new BTKeyValue<>(key,value);
           x.mCurrentKeyNum++;
           this.size++;
        }else{
            /*
              当前节点不是叶子节点，则找到对应的叶子节点进行插入，这个是递归的过程
             */
            BTNode<K,V>t=insert(x.mChildren[possibleIdx],key,value);
            /*
             叶子节点插入后，若返回的节点的键-值对的数量为1时，则说明新插入的节点经过了分裂，则将返回的节点合并到父亲节点中
             */
            if(t.mCurrentKeyNum==1){
                for(int i=x.mCurrentKeyNum;i>possibleIdx;i--){
                    x.mKeyValues[i]=x.mKeyValues[i-1];
                }
                x.mKeyValues[possibleIdx]=t.mKeyValues[0];
                //节点的children的数量比其key-values的数量多1
                for(int i=x.mCurrentKeyNum+1;i>possibleIdx+1;i--){
                    x.mChildren[i]=x.mChildren[i-1];
                }
                x.mChildren[possibleIdx]=t.mChildren[0];
                x.mChildren[possibleIdx+1]=t.mChildren[1];
                x.mCurrentKeyNum++;
            }
        }
        return x;
    }

    private BTNode<K,V> createNode(){
        BTNode<K,V>btNode=new BTNode<>();
        btNode.misLeaf=true;
        btNode.mCurrentKeyNum=0;
        return btNode;
    }

    /*
      B树的最小值
     */
    public BTKeyValue<K,V>minKey(){
        return minKey(mRoot);
    }

    private BTKeyValue<K,V>minKey(BTNode<K,V> root){
        if(root==null){
            return null;
        }
        if(root.mChildren[0]!=null){
            return minKey(root.mChildren[0]);
        }else{
            return root.mKeyValues[0];
        }
    }

    /*
     B树的最大值
     */
    public BTKeyValue<K,V>maxKey(){
        return maxKey(mRoot);
    }

    private BTKeyValue<K,V>maxKey(BTNode<K,V>root){
        if(root==null){
            return null;
        }
        if(root.mChildren[root.mCurrentKeyNum]!=null){
            return maxKey(root.mChildren[root.mCurrentKeyNum]);
        }else {
            return root.mKeyValues[root.mCurrentKeyNum-1];
        }
    }


    /*
      删除节点
      @param key
      @return key对应的value值
     */
    public V delete(K key){
        V v=search(key);
        mRoot=delete(mRoot,key);
        return v;
    }

    private BTNode<K,V>delete(BTNode<K,V>x,K key){
       //1.获取要删除的键在当前节点上的索引位置
       int possibleIndex=binarySearch(x,key);
       //2.根据当前节点是否是叶子节点分情况讨论
       if(x.misLeaf==true){
           //2.1 当前节点是叶子节点且在当前节点中找到要删除的键，则直接删除
           if (possibleIndex < x.mCurrentKeyNum && key.compareTo(x.mKeyValues[possibleIndex].mKey) == 0) {
               for(int i=possibleIndex;i<x.mCurrentKeyNum-1;i++){
                   x.mKeyValues[i]=x.mKeyValues[i+1];
               }
               x.mKeyValues[x.mCurrentKeyNum-1]=null;
               x.mCurrentKeyNum--;
               size--;
           }
       }else {
         //2.2 当前节点不是叶子节点,且在当前节点上找到要删除的键
         if(possibleIndex<x.mCurrentKeyNum&&key.compareTo(x.mKeyValues[possibleIndex].mKey)==0){
             //删除的处理方法：用possibleIndex处的子结点的最大键替换要删除的键
             BTKeyValue<K,V>keyNeedtoSwap=maxKey(x.mChildren[possibleIndex]);
             //1)将子节点的最大键删除
             x=delete(x,keyNeedtoSwap.mKey);
             //2)当前节点此时的键可能会发生改变，所以需要再搜索一次
             possibleIndex=binarySearch(x,key);
             //3)将key替换
             x.mKeyValues[possibleIndex]=keyNeedtoSwap;
         }else{
             //2.3 当前节点不是叶子节点，则递归向下在possibleIndex处的索引子节点上删除key
             BTNode<K,V> t=delete(x.mChildren[possibleIndex],key);
             /*
              检测删除后返回的节点的状态，如果键数量小于下限，则根据情况，进行选择或合并
              */
             if(t.mCurrentKeyNum<t.LOWER_BOUND_KEYNUM){
                 /*
                 判断返回节点的兄弟节点的状况，若兄弟结点的键数量>下限，则进行旋转操作（向兄弟节点借键），若兄弟结点的键数量<=下限，则与兄弟节点进行合并操作
                  */
                 //1.判断左兄弟节点
                 if(t.hasLeftSiblingAtIndex(x,possibleIndex)){
                    //如果左兄弟节点键数量>下限，则向左兄弟节点借键，即进行右旋操作
                    if(t.getLeftSiblingAtIndex(x,possibleIndex).mCurrentKeyNum>t.LOWER_BOUND_KEYNUM){
                        rightRotate(x,possibleIndex);
                    }else {
                     //左兄弟节点键数量<下限，则与左兄弟进行合并
                        leftMerge(x,possibleIndex);
                    }
                } //2.判断右兄弟节点
                 else if(t.hasRightSiblingAtIndex(x,possibleIndex)){
                     //如果右兄弟节点键数量>下限，则向右兄弟节点借键，即进行左旋操作
                     if(t.getRightSiblingAtIndex(x,possibleIndex).mCurrentKeyNum>t.LOWER_BOUND_KEYNUM){
                         leftRotate(x,possibleIndex);
                     }else {
                     //右兄弟节点键数量<下限，则与右兄弟节点进行合并
                         rightMerge(x,possibleIndex);
                     }
                }
                 //判断删完后根节点是否没有键存在，若没有,则从子节点中重新选取一个根节点
                 if(x==mRoot&&x.mCurrentKeyNum == 0){
                     x=x.mChildren[0];
                 }
             }
         }
       }
       return x;
    }

    /*
       如果当删除了键的结点的键数量下溢时，同时它的其中一个兄弟结点的键数大于下限，我们可以将其兄弟中的一个键移动（借用）到该节点。
       这种情况下，使用旋转操作，左旋转或右旋转。
    */

    //左旋转：向右兄弟结点借键，
    private BTNode<K,V> leftRotate(BTNode<K,V>x,int possibleIdx){
        //获取右兄弟节点和右兄弟节点最小的键-值对/节点
        BTNode<K,V>rightNode=x.mChildren[possibleIdx+1];
        BTKeyValue<K,V>rightKey=rightNode.mKeyValues[0];
        BTNode<K,V>rightFirstNode=rightNode.mChildren[0];
        //获取父节点交换位置的键-值对
        BTKeyValue<K,V>parentKey=x.mKeyValues[possibleIdx];

        //possibleNode是键所在的那个节点，它的父节点是x
        BTNode<K,V>possibleNode=x.mChildren[possibleIdx];
        //将topKey填到possibleNode的键-值对的末尾
        possibleNode.mKeyValues[possibleNode.mCurrentKeyNum]=parentKey;
        possibleNode.mChildren[possibleNode.mCurrentKeyNum+1]=rightFirstNode;
        //更新possibleNode的键值对的数量
        possibleNode.mCurrentKeyNum++;

        //将父节点拿走键值对的位置上填上从右兄弟节点中提取出的键值对
        x.mKeyValues[possibleIdx]=rightKey;

        //将右兄弟节点提出的键值对和节点在右兄弟节点中删除
        for(int i=1;i<rightNode.mCurrentKeyNum;i++){
            rightNode.mKeyValues[i-1]=rightNode.mKeyValues[i];
        }
        rightNode.mKeyValues[rightNode.mCurrentKeyNum-1]=null;
        for(int i=1;i<rightNode.mCurrentKeyNum+1;i++){
            rightNode.mChildren[i-1]=rightNode.mChildren[i];
        }
        rightNode.mChildren[rightNode.mCurrentKeyNum]=null;
        rightNode.mCurrentKeyNum--;
        return x;
    }

    //右旋转，向左兄弟节点借键
    private BTNode<K,V> rightRotate(BTNode<K,V>x,int possibleIdx){
        //获取左兄弟节点和左兄弟节点最大的键值对／节点
        BTNode<K,V>leftNode=x.mChildren[possibleIdx-1];
        BTKeyValue<K,V>leftkey=leftNode.mKeyValues[leftNode.mCurrentKeyNum-1];
        BTNode<K,V>leftLastNode=leftNode.mChildren[leftNode.mCurrentKeyNum];
        //获取父节点要交换位置的键值对
        BTKeyValue<K,V>parentKey=x.mKeyValues[possibleIdx-1];

        //possibleNode是键所在的那个节点，它的父节点是x
        BTNode<K,V>possibleNode=x.mChildren[possibleIdx];
        //将possibleNode中的键值对将最低位空出来：以用来填充从父结点交换过来的键值对,对其子节点也是一样
        for(int i=possibleNode.mCurrentKeyNum;i>0;i--){
            possibleNode.mKeyValues[i]=possibleNode.mKeyValues[i-1];
        }
        for(int i=possibleNode.mCurrentKeyNum+1;i>0;i--){
            possibleNode.mChildren[i]=possibleNode.mChildren[i-1];
        }
        possibleNode.mKeyValues[0]=parentKey;
        possibleNode.mChildren[0]=leftLastNode;
        possibleNode.mCurrentKeyNum++;

        //将从父节点拿走对键值对对位置上填上从左兄弟节点提取出对键值对
        x.mKeyValues[possibleIdx-1]=leftkey;

        //将从左兄弟节点借的键值对和子节点在左节点删除
        leftNode.mKeyValues[leftNode.mCurrentKeyNum-1]=null;
        leftNode.mChildren[leftNode.mCurrentKeyNum]=null;
        leftNode.mCurrentKeyNum--;
        return x;
    }

    /*
      当删除了键的结点的键数量下溢时，同时它的其中一个兄弟结点的键数小于等于下限（此时借用兄弟结点的键会导致，兄弟结点的键下溢），
      因此我们需要将删除了键的结点和其键数不大于下限的兄弟结点合并。
      注：结点[12]与其左兄弟结点[3，6，9]合并等价于结点[3，6，9]与其右兄弟结点[12]合并。他意味着，我们在写代码的时候可以将左右合并互相转换。
     */

    //右合并
    private BTNode<K,V>rightMerge(BTNode<K,V>x,int possibleIdx){
        return leftMerge(x,possibleIdx+1);
    }

    //左合并:合并父结点中位于possibleIdx和possibleIdx-1处的俩结点
    private BTNode<K,V>leftMerge(BTNode<K,V>x,int possibleIdx){
        //获取左兄弟节点
        BTNode<K,V>leftNode=x.mChildren[possibleIdx-1];
        //获取父节点要结合到左兄弟节点的键值对
        BTKeyValue<K,V>topKey=x.mKeyValues[possibleIdx-1];
        //获取需要合并的节点
        BTNode<K,V>possibleNode=x.mChildren[possibleIdx];

        //将父节点获取的键值对放入左兄弟节点
        leftNode.mKeyValues[leftNode.mCurrentKeyNum]=topKey;

        //将要合并的节点的键值对和子节点全部放入左兄弟节点
        for(int i=0;i<possibleNode.mCurrentKeyNum;i++){
            leftNode.mKeyValues[i+leftNode.mCurrentKeyNum+1]=possibleNode.mKeyValues[i];
        }
        for(int i=0;i<possibleNode.mCurrentKeyNum+1;i++){
            leftNode.mChildren[i+leftNode.mCurrentKeyNum+1]=possibleNode.mChildren[i];
        }
        //更新左兄弟节点的键值对的数量
        leftNode.mCurrentKeyNum+=(1+possibleNode.mCurrentKeyNum);
        //更新父节点的键值对和子节点
        for(int i=possibleIdx;i<x.mCurrentKeyNum;i++){
            x.mKeyValues[i-1]=x.mKeyValues[i];
        }
        x.mKeyValues[x.mCurrentKeyNum-1]=null;
        for(int i=possibleIdx;i<x.mCurrentKeyNum;i++){
            x.mChildren[i]=x.mChildren[i+1];
        }
        x.mChildren[x.mCurrentKeyNum]=null;
        x.mCurrentKeyNum--;

        return x;
    }

    public static void main(String[] args) {
        BTree<Integer,String>bt=new BTree<>(6);
        for(int i=1;i<=56;i++){
            bt.add(i,""+i+"");
        }
        System.out.println("Tree size:"+bt.size);
        bt.delete(3);
        System.out.println("size after delete:" + bt.size);
    }

}
