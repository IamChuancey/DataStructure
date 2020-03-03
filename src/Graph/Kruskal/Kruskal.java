package Graph.Kruskal;

import java.util.LinkedList;
import java.util.Queue;

public class Kruskal {
   private int weight; //最小生成树的权重
   private Queue<Edge>mst=new LinkedList<>();//最小生成树的边
   public Kruskal(EdgeWeightedGraph graph){
       MinPQ pq=new MinPQ(graph.E());
       for(Edge e:graph.edges()){
          pq.add(e);
       }
       UnionFind uf=new UnionFind(graph.V());
       while (!pq.isEmpty()&&mst.size()<graph.V()){
           Edge e=pq.poll();
           int v=e.either();
           int w=e.other(v);
           if(uf.find(v)!=uf.find(w)){ //不会形成一个cycle
               uf.Union(v, w);
               mst.add(e);
               weight+=e.weight();
           }
       }
   }

    public Iterable<Edge> edges() {
        return mst;
    }

    public static void main(String[] args) {
        EdgeWeightedGraph graph=new EdgeWeightedGraph(5);
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
        Kruskal mst=new Kruskal(graph);
        for(Edge e:mst.edges()){
            int v=e.either();
            System.out.format("%d-%d: %d, ",v,e.other(v),e.weight());
        }
        System.out.println();
        System.out.println("最小生成树的权重："+mst.weight);
    }

}
