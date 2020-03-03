package Graph;

import java.util.LinkedList;
import java.util.Queue;

public class Graph2 {
    private int v;//顶点的个数
    private int[][]adj; //邻接矩阵
    public Graph2(int v){
        this.v=v;
        adj=new int[v][v];
    }

    public void addEdge(int s,int t){ //无向图存边
        adj[s][t]=1;
        adj[t][s]=1;
    }

    //bfs
    public void bfs(int s,int t){ //s->t
        if(s==t) return;
        int visited[]=new int[v]; //记录节点是否被访问过
        visited[s]=1;
        int prev[]=new int[v]; //记录s->t搜索过的路径
        for (int i=0;i<prev.length;i++){
            prev[i]=-1;
        }
        Queue<Integer>queue=new LinkedList<>();
        queue.add(s);
        while(!queue.isEmpty()){
            int q=queue.poll();
            for(int i=0;i<adj[q].length;i++){
                if(adj[q][i]==1&&visited[i]!=1){
                    queue.add(i);
                    prev[i]=q;
                    visited[i]=1;
                    if(i==t){
                       print(prev, s, t);
                       return;
                    }
                }
            }
        }
    }

    //dfs
    private boolean found=false;
    //深度优先搜索
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
        for(int i=0;i<adj[w].length;i++){
            if(!visited[i]&&adj[w][i]==1){
                prev[i]=w;
                recurDfs(i, t, visited,prev);
            }
        }
    }


    private void print(int prev[],int s,int t){
        if(s!=t&&prev[t]!=-1){
            print(prev,s,prev[t]);
        }
        System.out.println(t+"");
    }

    public static void main(String[] args) {
        Graph2 graph2=new Graph2(6);
        graph2.addEdge(1, 2);
        graph2.addEdge(1, 4);
        graph2.addEdge(2, 5);
        graph2.addEdge(4, 5);
        graph2.dfs(1, 5);
    }
}
