package Graph.Kruskal;

public class Edge implements Comparable<Edge> {
    private int v; //边的其中某个顶点
    private int w; //边的另一个顶点
    private int weight; //边的权重

    Edge(int v,int w,int weight){
        this.v=v;
        this.w=w;
        this.weight=weight;
    }
    public int weight(){
        return this.weight;
    }
    //返回边的某个顶点
    public int either(){
        return v;
    }
    //在给出边的某个顶点的情况下，返回边的另一个顶点
    public int other(int vertex){
        if(vertex==v) return w;
        else return v;
    }
    //比较两条边的权重
    public int compareTo(Edge that){
        return Double.compare(that.weight, that.weight);
    }
    //表示边
    public String toString(){
        return String.format("%d-%d: %d", v,w,weight);
    }

    public static void main(String[] args) {
        Edge e=new Edge(12,34,5);
        System.out.println(e.toString());
    }


}
