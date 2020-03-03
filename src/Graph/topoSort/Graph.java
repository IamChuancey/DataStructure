package Graph.topoSort;

import java.util.LinkedList;
import java.util.Queue;

public class Graph { //有向图
    private int v; //顶点的个数
    private LinkedList<Integer>adj[];//邻接表
    public Graph(int v){
        this.v=v;
        adj=new LinkedList[v];
        for(int i=0;i<v;i++){
            adj[i]=new LinkedList<>();
        }
    }
    public void addEdge(int s,int t){
        adj[s].add(t); //t依赖于s, s先于t, s->t
    }

    //拓扑排序的实现2：Kahn算法
    /*
      有向(无环)图共v个顶点，顶点为0～v-1;
     */
    public void topoSortByKahn(){
        int[]inDegree=new int[v];
        for(int i=0;i<v;i++){
            for(int j=0;j<adj[i].size();j++){
                int w=adj[i].get(j); //i->w
                inDegree[w]++;
            }
        }
        Queue<Integer>queue=new LinkedList<>();
        for(int i=0;i<v;i++){
            if(inDegree[i]==0) queue.add(i);
        }
        while (!queue.isEmpty()){
            int i=queue.poll();
            System.out.print("->"+i);
            for(int j=0;j<adj[i].size();j++){
                int k=adj[i].get(j);
                inDegree[k]--;
                if(inDegree[k]==0) queue.add(k);
            }
        }
    }

    public void topoSortByDFS(){
        //构建逆邻接表，边t->s表示 t依赖于s, t后于s
        LinkedList<Integer>inverseAdj[]=new LinkedList[v];
        for(int i=0;i<v;i++){
            inverseAdj[i]=new LinkedList<>();
        }
        for(int i=0;i<v;i++){ //通过邻接表构建逆邻接表
            for(int j=0;j<adj[i].size();j++){
                int w=adj[i].get(j);
                inverseAdj[w].add(i);
            }
        }
        boolean[]visited=new boolean[v];
        for(int i=0;i<v;i++){
            if(visited[i]==false){
                visited[i]=true;
                dfs(i,inverseAdj,visited);
            }
        }
    }

    private void dfs(int vertex,LinkedList<Integer>inverseAdj[],boolean[]visited){
        for(int i=0;i<inverseAdj[vertex].size();i++){
            int w=inverseAdj[vertex].get(i);
            if(visited[w]==true) continue;
            visited[w]=true;
            dfs(w,inverseAdj,visited);
        }
        System.out.print("->"+vertex);
    }

    public static void main(String[] args) {
        Graph graph=new Graph(4);
        graph.addEdge(1, 0);
        graph.addEdge(2,1);
        graph.addEdge(1,3);
        graph.topoSortByDFS();
    }

}
