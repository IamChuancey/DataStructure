package Graph.Prim;

import java.util.LinkedList;

public class EdgeWeightedGraph {
    private LinkedList<Edge>graph[]; //无向图
    private int V; //顶点数
    private int edges; //边的个数
    public EdgeWeightedGraph(int v){
       this.V=v;
       this.edges=0;
       this.graph=new LinkedList[v]; //邻接表表示图
       for(int i=0;i<V;i++){
           graph[i]=new LinkedList<>();
       }
    }
    public int V(){
        return this.V;
    }
    public int E(){
        return this.edges;
    }
    public void addEdge(Edge e){
        int vertex=e.either();
        graph[vertex].add(e);
        graph[e.other(vertex)].add(e);
        edges++;
    }
    //遍历包含某个节点V的所有边
    public Iterable<Edge> adj(int v){
        return graph[v];
    }
    //遍历所有的边
    public Iterable<Edge> edges(){
        LinkedList<Edge> list=new LinkedList<>();
        for(int v=0;v<V;v++){
           for(Edge e: graph[v]){
               if(e.other(v)>v){
                  list.add(e);
               }
           }
        }
        return list;
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
        System.out.println("这条图共"+graph.E()+"边。");
        for (Edge e: graph.edges()){
            int v=e.either();
            System.out.format("%d-%d: %d",v,e.other(v),e.weight());
            System.out.println();
        }
        System.out.println("包含顶点0的所有边：");
        for (Edge e: graph.adj(0)){
            int v=e.either();
            System.out.format("%d-%d: %d",v,e.other(v),e.weight());
            System.out.println();
        }
    }

}
