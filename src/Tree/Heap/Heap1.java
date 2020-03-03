package Tree.Heap;

//小顶堆
public class Heap1 {
    private int[]nodes;
    private int count; //堆中节点的个数
    public Heap1(int v){
        this.nodes=new int[v+1];
        this.count=0;
    }
    //删除堆顶节点
    public int poll(){
        if(count==0) {
            System.out.println("The heap is empty!");
            return -1;
        }
        int Top=nodes[1];
        //交换堆顶元素和堆尾元素
        swap(1, count);
        count--;
        //从上到下堆化
        int i=1;
        while (2*i<count){
            if(nodes[2*i]<nodes[i]||nodes[2*i+1]<nodes[i]){
                if(nodes[2*i]<nodes[2*i+1]) {
                    swap(i, 2*i);
                    i=2*i;
                }else {
                    swap(i, 2*i+1);
                    i=2*i+1;
                }
            }
        }
        return Top;
    }

    private void swap(int i,int j){
        int tmp=nodes[i];
        nodes[i]=nodes[j];
        nodes[j]=tmp;
    }

    //添加节点
    public void add(int vertex){
        nodes[++count]=vertex;
        //从下到上堆化
        int i=count;
        while (i/2>0&&nodes[i]<nodes[i/2]){
            swap(i,i/2);
            i=i/2;
        }
    }

    //更新节点的值，因为更新后到值是比原先到值更小，所以只需从节点的下标开始，往上堆化即可。
    public void update(int vertex){
        //首先找到其节点下标
        int index=-1;
        for (int i=1;i<nodes.length;i++){
            if(nodes[i]==vertex&&nodes[i]==vertex){
                index=i;
                break;
            }
        }
        //从下到上堆化
        while (index/2>0&&nodes[index]<nodes[index/2]){
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

    public static void main(String[] args) {
        Heap1 heap1 = new Heap1(7);
        heap1.add(9);
        heap1.add(8);
        heap1.add(7);
        heap1.add(6);
        heap1.add(5);
        heap1.add(4);
        heap1.add(3);
        for (int i = 1; i <= heap1.count; i++) {
            System.out.print(heap1.nodes[i] + " ");
        }
        System.out.println("delete the top heap element." + heap1.poll());
        for (int i = 1; i <= heap1.count; i++) {
            System.out.print(heap1.nodes[i] + " ");
        }
        System.out.println("delete the top heap element." + heap1.poll());
        for (int i = 1; i <= heap1.count; i++) {
            System.out.print(heap1.nodes[i] + " ");
        }
        System.out.println("delete the top heap element." + heap1.poll());
        for (int i = 1; i <= heap1.count; i++) {
            System.out.print(heap1.nodes[i] + " ");
        }
        System.out.println("delete the top heap element." + heap1.poll());
        for (int i = 1; i <= heap1.count; i++) {
            System.out.print(heap1.nodes[i] + " ");
        }
        System.out.println("delete the top heap element." + heap1.poll());
        for (int i = 1; i <= heap1.count; i++) {
            System.out.print(heap1.nodes[i] + " ");
        }
        System.out.println("delete the top heap element." + heap1.poll());
        for (int i = 1; i <= heap1.count; i++) {
            System.out.print(heap1.nodes[i] + " ");
        }
        System.out.println("delete the top heap element." + heap1.poll());
        for (int i = 1; i <= heap1.count; i++) {
            System.out.print(heap1.nodes[i] + " ");
        }
        System.out.println("delete the top heap element." + heap1.poll());
    }
}
