package Graph.Prim;

//优先级队列：小顶堆
public class MinPQ{
    private Edge[]edges;
    private int count; //堆中边的个数
    public MinPQ(int v){
        this.edges=new Edge[v+1];
        this.count=0;
    }

    //删除堆顶节点
    public Edge poll(){
        if(count==0) return null;
        Edge Top=edges[1];
        //交换堆顶元素和堆尾元素
        swap(1, count);
        count--;
        //从上到下堆化
        int i=1;
        while (2*i<=count){
            if(edges[2*i].weight()<edges[i].weight()||edges[2*i+1].weight()<edges[i].weight()){
                if(2*i+1<=count&&edges[2*i+1].weight()<edges[2*i].weight()) {
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
        Edge tmp=edges[i];
        edges[i]=edges[j];
        edges[j]=tmp;
    }

    //添加边到堆
    public void add(Edge e){
        edges[++count]=e;
        //从下到上堆化
        int i=count;
        while (i/2>0&&edges[i].weight()<edges[i/2].weight()){
            swap(i,i/2);
            i=i/2;
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


