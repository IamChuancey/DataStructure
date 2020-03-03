package Graph.Prim;

import java.util.LinkedList;
import java.util.Queue;

public class PrimMST {
    private int weight; //最小生成树的权重
    private Queue<Edge>mst; //最小生成树
    private boolean[]marked; //marked[v] = true iff v on mst tree
    private MinPQ pq;

    public PrimMST(EdgeWeightedGraph graph){
        mst=new LinkedList<>();
        pq=new MinPQ(graph.E());
        marked=new boolean[graph.V()];
        //选取一个顶点开始出发
        prim(graph, 0);
//      for (int v=0;v<graph.V();v++){
//            if(!marked[v]) prim(graph, v);
//      }
    }

    /*
       arg0: EdgeWeightedGraph graph 带权无向图
       arg1: int s 某个顶点
     */
    private void prim(EdgeWeightedGraph graph, int s){
         //扫描连接顶点s不在树中的所有边
         scan(graph,s);
         while (!pq.isEmpty()){
             Edge e=pq.poll();
             int v=e.either(),w=e.other(v);
             if(marked[v]||marked[w]){
                 if(marked[v]&&marked[w]) continue;
                 mst.add(e);
                 weight+=e.weight();
                 if(!marked[v]) scan(graph,v);
                 if(!marked[w]) scan(graph,w);
             }
         }
    }


    private void scan(EdgeWeightedGraph graph,int s){
        if(!marked[s]){
            marked[s]=true;
            for(Edge e: graph.adj(s)){
                if(!marked[e.other(s)])
                pq.add(e);
            }
        }
    }

    public Iterable<Edge>edges(){
        return  mst;
    }

    public int getWeight(){
        return weight;
    }

    public static void main(String[] args) {
        EdgeWeightedGraph  graph=new EdgeWeightedGraph(5);
        Edge e0=new Edge(0, 2, 1);
        Edge e1=new Edge(0,1,2);
        Edge e2=new Edge(1, 2,3);
        Edge e3=new Edge(2,3,4);
        Edge e4=new Edge(0,3,5);
        Edge e5=new Edge(1,4,6);
        Edge e6=new Edge(3,4,7);
        graph.addEdge(e0);
        graph.addEdge(e1);
        graph.addEdge(e2);
        graph.addEdge(e3);
        graph.addEdge(e4);
        graph.addEdge(e5);
        graph.addEdge(e6);
        PrimMST mst=new PrimMST(graph);
        for(Edge e:mst.edges()){
            int v=e.either();
            System.out.format("%d-%d: %d, ",v,e.other(v),e.weight());
        }
        System.out.println();
        System.out.println("最小生成树的权重："+mst.weight);
    }

}
