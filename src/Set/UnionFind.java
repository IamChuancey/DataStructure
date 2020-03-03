package Set;

/*
   并查集的数组实现：数组下标表示元素，数组存储的内容表示元素所属的集合
   如：arr[1]=2 表示元素1属于集合2
 */
public class UnionFind {
   private int[]id; //并查集的元素
   private int size; //并查集的元素个数
   public UnionFind(int size){
      this.size=size;
      id=new int[size];
      for(int i=0;i<size;i++){ //初始化时，每个元素自成集合
          id[i]=i;
      }
   }
    //查找元素属于哪个集合
    private int find(int element){
       return id[element];
    }
    //判断两个元素是否属于同一个集合
    public boolean isConnected(int firstElement,int secondElement){
       return find(firstElement)==find(secondElement);
    }
    //合并两个元素所在的集合:将第一个集合合并到第二个集合
    public void  Union(int firstEleement,int secondElement){
        int firstUnion=find(firstEleement);
        int secondUnion=find(secondElement);
        if(firstEleement!=secondElement){
            for (int i=0;i<this.size;i++){
                if(id[i]==firstUnion){
                    id[i]=secondUnion;
                }
            }
        }
    }

    public void printArr(){
       for (int id:this.id){
           System.out.print(id+ "\t");
       }
       System.out.println();
    }

    public static void main(String[] args) {
        int n = 10;
        UnionFind union = new UnionFind(n);
        System.out.println("初始：");
        union.printArr();

        System.out.println("连接了5 6");
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

        System.out.println("1  6 是否属于同一集合：" + union.isConnected(1, 6));

        System.out.println("1  8 是否属于同一集合：" + union.isConnected(1, 8));
    }

}
