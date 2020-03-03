package Graph.Astar;

import java.util.LinkedList;

public class Astar {
    private LinkedList<Edge>adj[]; //邻接表存储有向图
    private int v; //顶点个数
    private  Vertex[]vertexs;
    public Astar(int v){
        this.v=v;
        this.vertexs=new Vertex[v];
        this.adj=new LinkedList[v];
        for (int i=0;i<v;i++){
            this.adj[i]=new LinkedList<>();
        }
    }

    public void addVetex(int id,int x,int y){
        vertexs[id]=new Vertex(id,x,y);
    }

    public void addEdge(int sid,int tid){
        Edge e=new Edge(sid, tid, hManhattan(vertexs[sid],vertexs[tid]));
        adj[sid].add(e);
    }

    public int hManhattan(Vertex v1, Vertex v2) { // Vertex表示顶点，后面有定义
        return Math.abs(v1.x - v2.x) + Math.abs(v1.y - v2.y);
    }

    private class Edge{
        public int sid; //边的起始顶点编号
        public int tid; //边的终止顶点编号
        public int w;//权重
        public Edge(int sid,int tid,int w){
            this.sid=sid;
            this.tid=tid;
            this.w=w;
        }
    }

    //将每个节点表示为(节点Id,距离)的形式
    private class Vertex{
        public int id; //顶点编号ID
        public int dist; //从起始顶点到这个顶点的距离,也是g(i)
        public  int f; //f(i)=g(i)+h(i),h(i)是这个顶点到终点的曼哈顿距离
        public int x,y; //顶点在地图中的坐标（x，y）
        public Vertex(int id,int x,int y){
            this.id=id;
            this.x=x;
            this.y=y;
            this.f=Integer.MAX_VALUE;
            this.dist=Integer.MAX_VALUE;
        }
    }

    //Java官方库PriorityQueue中并无update方法，因此实现之。
    private class PriorityQueue{
        private Vertex[]nodes;
        private int count; //堆中节点的个数
        public PriorityQueue(int v){
            this.nodes=new Vertex[v+1];
            this.count=0;
        }
        //删除堆顶节点
        public Vertex poll(){
            if(count==0) return null;
            Vertex Top=nodes[1];
            //交换堆顶元素和堆尾元素
            swap(1, count);
            count--;
            //从上到下堆化
            int i=1;
            while (2*i<=count){
                if(nodes[2*i].f<nodes[i].f||nodes[2*i+1].f<nodes[i].f){
                    if(2*i+1<=count&&nodes[2*i+1].f<nodes[2*i].f) {
                        swap(i, 2*i+1);
                        i=2*i+1;
                    }else {
                        swap(i, 2*i);
                        i=2*i;
                    }
                }
            }
            return Top;
        }

        private void swap(int i,int j){
            Vertex tmp=nodes[i];
            nodes[i]=nodes[j];
            nodes[j]=tmp;
        }

        //添加节点
        public void add(Vertex vertex){
            nodes[++count]=vertex;
            //从下到上堆化
            int i=count;
            while (i/2>0&&nodes[i].f<nodes[i/2].f){
                swap(i,i/2);
                i=i/2;
            }
        }

        //更新节点的值，因为更新后到值是比原先到值更小，所以只需从节点的下标开始，往上堆化即可。
        public void update(Vertex vertex){
            //首先找到其节点下标
            int index=-1;
            for (int i=1;i<nodes.length;i++){
                if(nodes[i].id==vertex.id&&nodes[i].f==vertex.f){
                    index=i;
                    break;
                }
            }
            nodes[index]=vertex;
            //从下到上堆化
            while (index/2>0&&nodes[index].f<nodes[index/2].f){
                swap(index, index/2);
                index=index/2;
            }
        }

        //判断堆是否为空
        public boolean isEmpty(){
            if(count==0)
                return true;
            else
                return  false;
        }

        //清空堆
        public void clear(){
            count=0;
        }

    }

    // 从顶点s到顶点t的最短路径
    public void astar(int s, int t) { //s->t的最短路径
        int[]predecessor=new int[this.v]; //记录走过的路径
        PriorityQueue queue=new PriorityQueue(this.v); //优先级队列:小顶堆 存储所有可到达的节点
        boolean[]inqueue=new boolean[this.v]; //标记是否进入过队列
        vertexs[s].dist=0; //起点，g(i)=dist=0;
        vertexs[s].f=0; //f(i)=g(i)+h(i)
        queue.add(vertexs[s]);
        inqueue[s]=true;
        while (!queue.isEmpty()){
            Vertex minVertex=queue.poll(); //在队列中选择最小的元素出队，即得到新的f最小的节点
            for(int i=0;i<adj[minVertex.id].size();i++){
                Edge e=adj[minVertex.id].get(i);
                Vertex nextVertex=vertexs[e.tid];
                if(minVertex.dist+e.w<nextVertex.dist){ // 更新next的dist,f
                    nextVertex.dist=minVertex.dist+e.w;
                    nextVertex.f=nextVertex.dist+hManhattan(nextVertex, vertexs[t]);
                    predecessor[nextVertex.id]=minVertex.id;
                    if(inqueue[nextVertex.id]==true){
                        queue.update(nextVertex);
                    }else {
                        queue.add(nextVertex);
                        inqueue[nextVertex.id]=true;
                    }
                }
                if(nextVertex.id==t){
                    queue.clear(); //清空堆，退出while循环
                    break;
                }
            }

        }
        System.out.print(s);
        print(s,t,predecessor);
    }

    //打印最短路径
    private void print(int s, int t, int[] predecessor) {
        if (s == t) return;
        print(s, predecessor[t], predecessor);
        System.out.print("->" + t);
    }

    public static void main(String[] args) {
        Astar astar0=new Astar(5);
        astar0.addVetex(0, 1, 1);
        astar0.addVetex(1, 2, 2);
        astar0.addVetex(2, 3, 4);
        astar0.addVetex(3,2,1);
        astar0.addVetex(4, 4, 5);

        astar0.addEdge(0,3);
        astar0.addEdge(3, 1);
        astar0.addEdge(1,2);
        astar0.addEdge(2,4);
        astar0.addEdge(3,4);

        astar0.astar(0,4);
    }

}
