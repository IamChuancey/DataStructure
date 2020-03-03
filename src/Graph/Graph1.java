package Graph;

import java.util.LinkedList;
import java.util.Queue;

public class Graph1 { //无向图
   private int v;  //顶点的个数
   private LinkedList<Integer>adj[];//邻接表

   public Graph1(int v){
       this.v=v;
       adj=new LinkedList[v];
       for(int i=0;i<v;i++){
           adj[i]=new LinkedList<>();
       }
   }

   public void addEdge(int s,int t){ //无向图存边
       adj[s].add(t);
       adj[t].add(s);
   }

   //bfs:广度优先搜索
   public void bfs(int s,int t){
       if(s==t) return;
       boolean visited[]=new boolean[v];
       visited[s]=true; //记录已经访问过的节点
       Queue<Integer>queue=new LinkedList<>(); //借助队列实现广度优先搜索
       queue.add(s);
       int[]prev = new int[v]; //记录搜索路径，当我们从顶点s开始，广度优先搜索到顶点t后，prev数组中存储的就是搜索路径。
       for (int i=0;i<prev.length;i++){
           prev[i]=-1;
       }
       while (!queue.isEmpty()){
           int w=queue.poll();
           for (int i = 0; i < adj[w].size(); i++) {
               int q = adj[w].get(i);
               if (!visited[q]) {
                   prev[q] = w;
                   if (q == t) {
                       print(prev, s, t); //这个路径是反向存储的。prev[w]存储的是，顶点 w 是从哪个前驱顶点遍历过来的
                       return;
                   }
               }
               visited[q] = true;
               queue.add(q);
           }
       }
   }

   private boolean found=false;
   //dfs：深度优先搜索
   public void dfs(int s,int t){  //s->t
      found=false;
      boolean[]visited=new boolean[v];
      int[]prev=new int[v];
      for(int i=0;i<v;i++){
          prev[i]=-1;
      }
      recurDfs(s, t, visited, prev);
      print(prev, s,t);
   }

   private void recurDfs(int w,int t,boolean[]visited,int[]prev){
      //if(found==true) return;
      visited[w]=true;
      if(w==t){
          found=true;
          return;
      }
      for(int i=0;i<adj[w].size();i++){
          int q=adj[w].get(i);
          if(!visited[q]){
              prev[q]=w;
              recurDfs(q, t, visited,prev);
          }
      }
   }

    //为了正向打印出路径，我们需要递归地来打印
    private void print(int[] prev, int s, int t){  // 递归打印s->t的路径
        if (prev[t] != -1 && t != s)
        {
            print(prev, s, prev[t]);
        }
        System.out.print(t + " ");
    }

    public static void main(String[] args) {
        Graph1 graph1=new Graph1(6);
        graph1.addEdge(1, 2);
        graph1.addEdge(1, 4);
        graph1.addEdge(2, 5);
        graph1.addEdge(4, 5);
        graph1.bfs(1, 5);
    }

}
