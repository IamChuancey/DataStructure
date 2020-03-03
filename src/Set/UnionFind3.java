package Set;

/*
    继UnionFind2,因为UnionFind2中的当两个集合合并时，谁的重量大，谁就来当合并之后的根。
    但还是有并查集高度太高的问题。并查集越高，就越接近线性，find函数就越接近O(n)。
    所以有了这种基于高度的union。合并时，谁的高度高，谁就是新的根。
    这种处理方法也就是我们常说的"按秩合并"的优化方法。
 */
public class UnionFind3 {
    private int[] parent; //父节点
    private int[] height; //高度
    int size;

    public UnionFind3(int size) {
        this.size = size;
        this.parent = new int[size];
        this.height = new int[size];
        for (int i = 0; i < size; i++) {
            parent[i] = i;
            height[i] = 1;
        }
    }

    //通过找父节点，找到最终当根节点，从而判断当前元素属于哪个集合，只有根节点的parent是它自己。
    public int find(int element) {
        while (element != parent[element]) {
            element = parent[element];
        }
        return element;
    }

    //判断是否属于同一个集合
    public boolean isConnected(int firstElement, int secondElement) {
        return find(firstElement) == find(secondElement);
    }

    /*
      两个集合的高度不一样的时候，对它们进行合并，新集合高度肯定等于高度大的那个集合的高度。所以高度不用调整。
      而两个集合高度相等时，哪个根来当新集合的根已经无所谓了，只需要让其中一个指向另一个就好了。
      然后会发现深度加了一层，所以新集合的根的高度就得+1。
     */
    public void  Union(int firstElement,int secondElement){
        int firstRoot=find(firstElement);
        int secondRoot=find(secondElement);

        if(height[firstRoot]<height[secondRoot]){
            parent[firstRoot]=secondRoot;
        }else if(height[firstRoot] > height[secondRoot]){
            parent[secondRoot]=firstRoot;
        }else{
            //当两个高度一样时，随便选取一个作为最终的根节点，这里我选取第一个
            parent[firstRoot]=secondRoot;
            height[secondRoot]+=1;
        }
    }

    public void printArr(int[] arr){
        for(int p : arr){
            System.out.print(p+"\t");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int n = 10;
        UnionFind3 union = new UnionFind3(n);

        System.out.println("初始parent：");
        union.printArr(union.parent);
        System.out.println("初始height：");
        union.printArr(union.height);

        System.out.println("连接了5 6 之后的parent：");
        union.Union(5, 6);
        union.printArr(union.parent);
        System.out.println("连接了5 6 之后的height：");
        union.printArr(union.height);

        System.out.println("连接了1 2 之后的parent：");
        union.Union(1, 2);
        union.printArr(union.parent);
        System.out.println("连接了1 2 之后的height：");
        union.printArr(union.height);

        System.out.println("连接了2 3 之后的parent：");
        union.Union(2, 3);
        union.printArr(union.parent);
        System.out.println("连接了2 3 之后的height：");
        union.printArr(union.height);

        System.out.println("连接了1 4 之后的parent：");
        union.Union(1, 4);
        union.printArr(union.parent);
        System.out.println("连接了1 4 之后的height：");
        union.printArr(union.height);

        System.out.println("连接了1 5 之后的parent：");
        union.Union(1, 5);
        union.printArr(union.parent);
        System.out.println("连接了1 5 之后的height：");
        union.printArr(union.height);

        System.out.println("1  6 是否连接：" + union.isConnected(1, 6));

        System.out.println("1  8 是否连接：" + union.isConnected(1, 8));
    }

}
