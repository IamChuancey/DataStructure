package Tree.BPlusTree;
import java.util.ArrayList;

/**
 * The simple implementation of B+-Tree, reference http://en.wikipedia.org/wiki/B%2B_tree
 *
 * @author bo.fangbo
 *
 * @param <T>
 * @param <V>
 */
public class BPlusTree <T extends Comparable<T>, V> {

    /**
     * the branching factor of the tree, measuring the capacity of nodes.
     *
     * a) the number of children for internal node must be in scope [ Math.ceil(factor/2), factor ].
     * b) the number of children for leaf node must be in scope [ Math.floor(factor/2), factor-1 ]
     */
    private int factor;

    private static final int DEFAULT_FACTOR = 5;

    private int MIN_CHILDREN_FOR_INTERNAL;
    private int MAX_CHILDREN_FOR_INTERNAL;
    private int MIN_FOR_LEAF;
    private int MAX_FOR_LEAF;

    private Node<T, V> root = null;

    public BPlusTree() {
        this(DEFAULT_FACTOR);
    }

    public BPlusTree(int factor) {
        this.factor = factor;

        this.MIN_CHILDREN_FOR_INTERNAL = Double.valueOf(Math.ceil(1.0 * this.factor / 2)).intValue();
        this.MAX_CHILDREN_FOR_INTERNAL = this.factor;
        this.MIN_FOR_LEAF = Double.valueOf(Math.floor(1.0 * this.factor / 2)).intValue();
        this.MAX_FOR_LEAF = this.factor - 1;

        this.root = new LeafNode<T, V>();
    }

    public void set(T key, V value) {
        if (key == null)    throw new NullPointerException("must not be null for key.");
        Node node = this.root.insert(key, value);

        if (node != null) this.root = node;
    }

    public V get(T key) {
        return this.root.get(key);
    }

    public V[] getRange(T low,T high) {
        return this.root.getRange(low,high);
    }

    public int height() {
        int height = 1;
        Node node = this.root;
        while( !(node instanceof LeafNode)) {
            height++;
            node = ((InternalNode)node).pointers[0];
        }

        return height;
    }

    /**
     * the abstract node definition, define the operation of leaf node and internal node.
     *
     * @author bo.fangbo
     *
     * @param <T>
     * @param <V>
     */
    abstract class Node<T extends Comparable<T>, V> {

        protected Node<T, V> parent;

        protected Object[] keys; //升序，节点是key-value类型数据，所以不管是叶子节点还是非叶子节点都有key,而非叶子节点还有value.

        protected int size;

        /**
         * if new parent node is created when insert the key-value, the created parent node is returned,
         * in other case, this method return null.
         *
         * @param key
         * @param value
         * @return
         */
        abstract Node<T, V> insert(T key, V value);

        //精确查找
        abstract V get(T key);

        //范围查找
        abstract V[] getRange(T low,T high);
    }

    /**
     * the internal node which manages the pointers.
     *
     * @author bo.fangbo
     *
     * @param <T>
     * @param <V>
     */
    class InternalNode<T extends Comparable<T>, V> extends Node<T, V> {
        private Node<T, V>[] pointers;

        public InternalNode() {
            this.size = 0;
            this.pointers = new Node[MAX_CHILDREN_FOR_INTERNAL + 1];
            this.keys = new Object[MAX_CHILDREN_FOR_INTERNAL];
        }

        public Node<T, V> insert(T key, V value) {
            int i = 0;
            for (; i < this.size; i++) {
                if ( key.compareTo( (T)this.keys[i] ) < 0 )  break;
            }

            return this.pointers[i].insert(key, value);
        }

        //精确查找
        public V get(T key) {
            int i = 0;
            for (; i < this.size; i++) {
                if ( key.compareTo( (T)this.keys[i] ) < 0)  break; //等价于key<keys[i]
            }

            return this.pointers[i].get(key);
        }

        //范围查找
        public V[] getRange(T low,T high){
            int i = 0;
            for (; i < this.size; i++) {
                if (high.compareTo( (T)this.keys[i] ) < 0)  break; //等价于key<keys[i]
            }
            return this.pointers[i].getRange(low,high);
        }


        /**
         * 叶子节点新分裂出的父节点将作为新的索引插入到现有的非叶子节点中
         * @param key
         * @param leftChild
         * @param rightChild
         * @return
         */
        private Node<T, V> insert(T key, Node<T, V> leftChild, Node<T, V> rightChild){
            if (this.size == 0) {
                this.size++;
                this.pointers[0] = leftChild;
                this.pointers[1] = rightChild;
                this.keys[0] = key;

                leftChild.parent = this;
                rightChild.parent = this;

                return this;
            }

            Object[] newKeys = new Object[MAX_CHILDREN_FOR_INTERNAL + 1];
            Node[] newPointers = new Node[MAX_CHILDREN_FOR_INTERNAL + 2];

            //为新的非叶子节点找到合适的插入位置
            int i = 0;
            for(; i < this.size; i++) {
                T curKey = (T)this.keys[i];
                if (curKey.compareTo(key) > 0) break;
            }

            //挪动数组，腾出位置，将新的非叶子节点插入到此位置中
            System.arraycopy(this.keys, 0, newKeys, 0, i);
            newKeys[i] = key;
            System.arraycopy(this.keys, i, newKeys, i + 1, this.size - i);

            System.arraycopy(this.pointers, 0, newPointers, 0, i + 1);
            newPointers[i + 1] = rightChild;
            System.arraycopy(this.pointers, i + 1, newPointers, i + 2, this.size - i);

            this.size++;
            if(this.size <= MAX_CHILDREN_FOR_INTERNAL) {
                System.arraycopy(newKeys, 0, this.keys, 0, this.size);
                System.arraycopy(newPointers, 0, this.pointers, 0, this.size + 1);
                return null;
            }

            //将当前非叶子节点的键值大于能够存储的上限时，将当前节点分裂，分裂成两个非叶子节点，
            // 并取新分裂出来的左边的叶子节点的最后一个key值作为新的索引插入到非叶子节点中(索引节点)
            int m = (this.size / 2);

            InternalNode<T, V> newNode = new InternalNode<T, V>();
            newNode.size = this.size - m - 1;
            System.arraycopy(newKeys, m + 1, newNode.keys, 0, this.size - m - 1);
            System.arraycopy(newPointers, m + 1, newNode.pointers, 0, this.size - m);

            // reset the children's parent to the new node.
            for(int j = 0; j <= newNode.size; j++) {
                newNode.pointers[j].parent = newNode;
            }

            this.size = m;
            this.keys = new Object[MAX_CHILDREN_FOR_INTERNAL];
            this.pointers = new Node[MAX_CHILDREN_FOR_INTERNAL];
            System.arraycopy(newKeys, 0, this.keys, 0, m);
            System.arraycopy(newPointers, 0, this.pointers, 0, m + 1);

            if (this.parent == null) {
                this.parent = new InternalNode<T, V>();
            }
            newNode.parent = this.parent;

            return ((InternalNode<T, V>)this.parent).insert((T)newKeys[m], this, newNode);
        }
    }

    /**
     * leaf node, store the keys and actual values.
     *
     * @author bo.fangbo
     *
     * @param <T>
     * @param <V>
     */
    class LeafNode<T extends Comparable<T>, V> extends Node<T, V> {
        private Object[] values;
        private LeafNode prev;
        private LeafNode next;

        public LeafNode() {
            this.size = 0;
            this.keys = new Object[MAX_FOR_LEAF];
            this.values = new Object[MAX_FOR_LEAF];
            this.parent = null;
        }

        //叶子节点的插入
        public Node<T, V> insert(T key, V value) {
            Object[] newKeys = new Object[MAX_FOR_LEAF + 1];
            Object[] newValues = new Object[MAX_FOR_LEAF + 1];

            //寻找新叶子节点插入的位置
            int i = 0;
            for (; i < this.size; i++) {
                T curKey = (T) this.keys[i];

                if (curKey.compareTo(key) == 0) {
                    this.values[i] = value;
                    return null;
                }

                if (curKey.compareTo(key) > 0) break;
            }

            /*
              将新叶子节点插入进节点数组中，对数组进行挪动
              public static void arraycopy(Object src,int srcPos,Object dest,int destPos,int length):
              src:源数组；
              srcPos:源数组要复制的起始位置；
              dest:目的数组；
              destPos:目的数组放置的起始位置；
              length:复制的长度。
              注意：src and dest都必须是同类型或者可以进行转换类型的数组．
            */
            System.arraycopy(this.keys, 0, newKeys, 0, i);
            newKeys[i] = key;
            System.arraycopy(this.keys, i, newKeys, i + 1, this.size - i);

            System.arraycopy(this.values, 0, newValues, 0, i);
            newValues[i] = value;
            System.arraycopy(this.values, i, newValues, i + 1, this.size - i);

            this.size++;

            if (this.size <= MAX_FOR_LEAF){
                System.arraycopy(newKeys, 0, this.keys, 0, this.size);
                System.arraycopy(newValues, 0, this.values, 0, this.size);
                return null;
            }

            //将当前叶子节点的键值大于能够存储的上限时，将当前节点分裂，分裂成两个叶子节点，
            // 并取新分裂出来的右边的那个叶子节点的第一个key值作为新的索引插入到非叶子节点中(索引节点)
            int m = this.size / 2;

            this.keys = new Object[MAX_FOR_LEAF];
            this.values = new Object[MAX_FOR_LEAF];
            System.arraycopy(newKeys, 0, this.keys, 0, m);
            System.arraycopy(newValues, 0, this.values, 0, m);

            LeafNode<T, V> newNode = new LeafNode<T, V>();
            newNode.size = this.size - m;
            System.arraycopy(newKeys, m, newNode.keys, 0, newNode.size);
            System.arraycopy(newValues, m, newNode.values, 0, newNode.size);

            this.size = m;
            if (this.parent == null) {
                this.parent = new InternalNode<T, V>();
            }
            newNode.parent = this.parent;

            newNode.prev=this;
            this.next=newNode;

            //叶子节点插入完成后，调用非叶子节点的插入方法
            return ((InternalNode<T, V>)this.parent).insert((T)newNode.keys[0], this, newNode);
        }

        //精确查找：典型的二分查找
        public V get(T key) {
            if (this.size == 0) return null;
            int start = 0;
            int end = this.size;
            int middle = (start + end) / 2;
            while (start < end) {
                T middleKey = (T)this.keys[middle];
                if (key.compareTo(middleKey) == 0){
                    break;
                }
                if (key.compareTo(middleKey) < 0) {
                    end = middle;
                } else {
                    start = middle;
                }
                middle = (start + end) / 2;
            }
            T middleKey = (T) this.keys[middle];
            return middleKey.compareTo(key) == 0 ? (V) this.values[middle] : null;
        }

        //范围查找
        public V[] getRange(T low,T high){
            ArrayList<V>list=new ArrayList<>();
            boolean flag=true;
            LeafNode node=this;
            int keyIndex=getIndex(high);
            int i=keyIndex;
            while (flag){
                for (; i >=0; i--) {
                    if (low.compareTo((T)node.keys[i]) <= 0&&high.compareTo((T)node.keys[i] ) >= 0)  {
                        list.add((V) node.values[i]);
                    }else {
                        flag=false;
                    }
                }
                node=node.prev;
                if(node==null) break;
                i=node.size-1;
            }
            return (V[]) list.toArray();
        }

        private int getIndex(T key) {
            if (this.size == 0) return -1;
            int start = 0;
            int end = this.size;
            int middle = (start + end) / 2;
            while (start < end) {
                T middleKey = (T)this.keys[middle];
                if (key.compareTo(middleKey) == 0){
                    break;
                }
                if (key.compareTo(middleKey) < 0) {
                    end = middle;
                } else {
                    start = middle;
                }
                middle = (start + end) / 2;
            }
            T middleKey = (T) this.keys[middle];
            return middleKey.compareTo(key) == 0 ?  middle : -1;
        }
    }

    public static void main(String[] args) {
        BPlusTree<Integer, String> myTree = new BPlusTree<Integer, String>(4);
        int max = 6;
        for(int i = 0; i < max; i++) {
            myTree.set(i, String.valueOf(i));
        }
        System.out.print("Data has been inserted into tree, ");
        System.out.println("tree height: " + myTree.height());
        //精确查找
        for (int i = 0; i < max; i++) {
            String str=myTree.get(i);
            if (!String.valueOf(i).equals(str)) {
                System.err.println("error for: " + i);
            }
        }
        System.out.println("Accurate Search Succeed!");
        //范围查找
        Object[]res=myTree.getRange(0,4); //查找key在[0，4]的key值
        for(int i=0;i<res.length;i++){
            System.out.print(res[i]+" ");
        }
        System.out.println();
        System.out.println("Range Lookup Succeed!");
    }
}


