package Set;

/*
    继UnionFind1,因为UnionFind1中的合并默认将第一个元素的集合合并到第二个元素的集合中，
    易出现单链表结构，因此，我们可以基于重量(子节点数量)进行合并，谁的子节点少，谁就是被合并的那个。
 */
public class UnionFind2 {
   private int[]parent; //父节点
   private int[]weight; //子节点数量
   private int size;

   public UnionFind2(int size){
       this.parent=new int[size];
       this.weight=new int[size];
       this.size=size;
       for(int i=0;i<size;i++){
           this.parent[i]=i;
           this.weight[i]=1;
       }
   }

   //通过找父节点，找到最终当根节点，从而判断当前元素属于哪个集合，只有根节点的parent是它自己。
   public int find(int element){
      while (element!=parent[element]){
          element=parent[element];
      }
      return element;
   }

   //判断两个元素是否属于同一个集合
   public  boolean isConnected(int firstEleement,int secondElement){
       return find(firstEleement)==find(secondElement);
   }

   public void  Union(int firstElement,int secondElement){
       int firstRoot=find(firstElement);
       int secondRoot=find(secondElement);
       if(firstRoot==secondRoot) return;
       if(weight[firstRoot]>weight[secondRoot]){
           parent[secondRoot]=firstRoot;
           weight[firstRoot]+=weight[secondRoot];
       }else {
           parent[firstRoot]=secondRoot;
           weight[secondRoot]+=weight[firstRoot];
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
        UnionFind2 union = new UnionFind2(n);

        System.out.println("初始parent：");
        union.printArr(union.parent);
        System.out.println("初始weight：");
        union.printArr(union.weight);

        System.out.println("连接了5 6 之后的parent：");
        union.Union(5, 6);
        union.printArr(union.parent);
        System.out.println("连接了5 6 之后的weight：");
        union.printArr(union.weight);

        System.out.println("连接了1 2 之后的parent：");
        union.Union(1, 2);
        union.printArr(union.parent);
        System.out.println("连接了1 2 之后的weight：");
        union.printArr(union.weight);

        System.out.println("连接了2 3 之后的parent：");
        union.Union(2, 3);
        union.printArr(union.parent);
        System.out.println("连接了2 3 之后的weight：");
        union.printArr(union.weight);

        System.out.println("连接了1 4 之后的parent：");
        union.Union(1, 4);
        union.printArr(union.parent);
        System.out.println("连接了1 4 之后的weight：");
        union.printArr(union.weight);

        System.out.println("连接了1 5 之后的parent：");
        union.Union(1, 5);
        union.printArr(union.parent);
        System.out.println("连接了1 5 之后的weight：");
        union.printArr(union.weight);

        System.out.println("1  6 是否连接：" + union.isConnected(1, 6));

        System.out.println("1  8 是否连接：" + union.isConnected(1, 8));
    }
}
