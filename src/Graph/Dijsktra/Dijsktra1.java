package Graph.Dijsktra;

//邻接矩阵表示带权有向图
public class Dijsktra1 {
    private int v;//顶点的个数
    private int[][]adj; //邻接矩阵
    public Dijsktra1(int v){
        this.v=v;
        adj=new int[v][v];
    }
    public void addEdge(int s,int t, int w){ //有向图存带权边
        adj[s][t]=w;
    }

    //将每个节点表示为(节点Id,距离)的形式
    private class Vertex{
        public int id; //顶点编号ID
        public int dist; //从起始顶点到这个顶点的距离
        public Vertex(int id,int dist){
            this.id=id;
            this.dist=dist;
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
                if(nodes[2*i].dist<nodes[i].dist||nodes[2*i+1].dist<nodes[i].dist){
                    if(2*i+1<=count&&nodes[2*i+1].dist<nodes[2*i].dist) {
                        swap(i, 2*i+1);
                        i=2*i+1;
                    }else {
                        swap(i, 2*i);
                        i=2*i;
                    }
                }else break;
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
            while (i/2>0&&nodes[i].dist<nodes[i/2].dist){
                swap(i,i/2);
                i=i/2;
            }
        }

        //更新节点的值，因为更新后到值是比原先到值更小，所以只需从节点的下标开始，往上堆化即可。
        public void update(Vertex vertex){
            //首先找到其节点下标
            int index=-1;
            for (int i=1;i<nodes.length;i++){
                if(nodes[i].id==vertex.id&&nodes[i].dist==vertex.dist){
                    index=i;
                    break;
                }
            }
            nodes[index]=vertex;
            //从下到上堆化
            while (index/2>0&&nodes[index].dist<nodes[index/2].dist){
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
    }

    // 从顶点s到顶点t的最短路径
    public void dijkstra(int s, int t) { //s->t的最短路径
        int[]predecessor=new int[this.v]; //记录走过的路径
        Vertex[]vertexs=new Vertex[this.v];
        for (int i=0;i<this.v;i++){
            vertexs[i]=new Vertex(i,Integer.MAX_VALUE);
        }
        PriorityQueue queue=new PriorityQueue(this.v); //优先级队列:小顶堆 存储所有可到达的节点
        boolean[]inqueue=new boolean[this.v]; //标记是否进入过队列
        vertexs[s].dist=0; //起点，dist=0;
        queue.add(vertexs[s]);
        inqueue[s]=true;
        while (!queue.isEmpty()){
            Vertex minVertex=queue.poll(); //在队列中选择最小的元素出队，即得到新的距离最小的节点
            if(minVertex.id==t) break; //找到最短路径
            for(int i=0;i<adj[minVertex.id].length;i++){
                if(adj[minVertex.id][i]!=0){
                    int w=adj[minVertex.id][i];
                    Vertex nextVertex=vertexs[i];
                    if(minVertex.dist+w<nextVertex.dist){
                        nextVertex.dist=minVertex.dist+w;
                        predecessor[nextVertex.id]=minVertex.id;
                        if(inqueue[nextVertex.id]==true){
                            queue.update(nextVertex);
                        }else {
                            queue.add(nextVertex);
                            inqueue[nextVertex.id]=true;
                        }
                    }
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
        Dijsktra1 dijsktra1=new Dijsktra1(6);
        dijsktra1.addEdge(0, 1, 10);
        dijsktra1.addEdge(0, 4, 15);
        dijsktra1.addEdge(1, 3, 2);
        dijsktra1.addEdge(1, 2, 15);
        dijsktra1.addEdge(3,2,1);
        dijsktra1.addEdge(3, 5, 12);
        dijsktra1.addEdge(2, 5, 5);
        dijsktra1.addEdge(4, 5, 10);
        dijsktra1.dijkstra(0, 5);
    }

}
