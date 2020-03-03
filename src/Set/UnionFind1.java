package Set;

/*
    继UnionFind,因为UnionFind中的合并操作太低效，合并一次就O(n)，
    因此可以采用树的结构，这才是我们常说的并查集。
*/
public class UnionFind1 {
   private int[] parent;
   private int size;
   public UnionFind1(int size){
       this.size=size;
       parent=new int[size]; //记录父节点
       for (int i=0;i<size;i++){
           parent[i]=i;
       }
   }
   //通过找父节点，找到最终当根节点，从而判断当前元素属于哪个集合，只有根节点的parent是它自己。
   public int find(int element){
       while (element!=parent[element]){
           element=parent[element];
       }
       return element;
   }

   //判断两个节点是否属于同一个集合
   public boolean isConnected(int firstElement,int secondElement){
      return  find(firstElement)==find(secondElement);
   }

   //合并集合:将第一个元素的集合合并到第二个元素的集合中
   public void  Union(int firstElement,int secondElement){
       int firstRoot=find(firstElement);
       int secondRoot=find(secondElement);
       if(firstRoot==secondRoot) return;
       parent[firstRoot]=secondRoot;
   }

   //打印元素
   public void printArr(){
       for (int parent: this.parent){
           System.out.print(parent+"\t");
       }
       System.out.println();
   }

    public static void main(String[] args) {
        int n=10;
        UnionFind1 union=new UnionFind1(n);
        System.out.println("初始：");
        union.printArr();

        System.out.println("连接了5，6");
        union.Union(5, 6);
        union.printArr();

        System.out.println("连接了1 2");
        union.Union(1, 2);
        union.printArr();

        System.out.println("连接了2 3");
        union.Union(2, 3);
        union.printArr();

        System.out.println("连接了1 4");
        union.Union(1, 4);
        union.printArr();

        System.out.println("连接了1 5");
        union.Union(1, 5);
        union.printArr();

        System.out.println("1  6 是否属于同一个集合：" + union.isConnected(1, 6));

        System.out.println("1  8 是否属于同一个集合：" + union.isConnected(1, 8));
    }

}
