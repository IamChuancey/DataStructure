package Tree.Heap;

//大顶堆
public class Heap {
    private int heap[];
    private int n; //堆可以存储的最大数据个数
    private int count;//堆中已经存储的数据个数
    public Heap(int capacity){
       this.heap=new int[capacity+1];
       n=capacity;
       count=0;
    }

    /*
       插入元素
     */
    public  void  insert(int data){
        if(count>=n) return; //堆满了
        count++;
        heap[count]=data;
        int i=count;
        //堆化，从下到上
        while (i/2>0&&heap[i]>heap[i/2]){
            swap(heap,i,i/2);
            i=i/2;
        }
    }
    private void swap(int heap[],int index1,int index2){
        int temp=heap[index1];
        heap[index1]=heap[index2];
        heap[index2]=temp;
    }

    /*
       删除堆顶元素
     */
    public void removeMax(){
        if(count==0) {
            System.out.println("堆已空！");
            return ;
        }
        heap[1]=heap[count];
        count--;
        heapify(heap,count,1);
    }

    //堆化，从上到下
    /*
       parm0: heap[] 待堆化数组
       parm1: count 待堆化元素总个数
       parm2: i 待堆化起始下标
     */
    private void heapify(int heap[],int count,int i){
        while (true){
            int maxPos=i;
            if(i*2<=count&&heap[i]<heap[2*i]) maxPos=i*2;
            if(i*2+1<=count&&heap[maxPos]<heap[i*2+1]) maxPos=i*2+1;
            if(maxPos==i) break; //说明没有可再交换的元素,则堆化停止
            swap(heap, i, maxPos);
            i=maxPos;
        }
    }

    /*
        删除元素(任意位置)
     */
    private void deleteByIndex(int heapIndex, int[]heap, int count){
        if(heapIndex>count){
            System.out.println("待删除元素不在堆中！");
            return;
        }
        //待删除元素和最后一个元素进行交换
        heap[heapIndex]=heap[count];
        count--;
        //从待删除元素位置的父节点位置从上向下进行堆化
        int parentIndex=heapIndex/2;
        if(parentIndex==0){
            heapify(heap, count, 1);
        }else {
           heapify(heap, count, parentIndex);
        }
    }

    //在堆中查找元素，返回堆中对应的下标, 因为堆用数据存储，直接顺序查找即可
    private int lookUp(int data){
        for(int i=1;i<=count;i++){
            if(heap[i]==data){
                return i;
            }
        }
        return -1;
    }

    /*
        删除元素
       : 先查找到元素的位置，然后删除指定位置的元素
     */
    public void delete(int data){
        int index=lookUp(data);
        if(index==-1){
            System.out.println("待删除元素不在堆中！");
            return;
        }
        deleteByIndex(index,this.heap,this.count);
    }

    /*
        堆排序
        堆排序分为两个步骤：第一步：建堆；第二步：排序
     */

    /*
        堆排序1：非原地算法(借助了this.heap这个数组)
     */
    private void buildHeap1(int[] array){
       for (int i=0;i<array.length;i++){
           insert(array[i]);
       }
    }

    public void HeapSort1(int[]array){
        //先建堆
        buildHeap1(array);
        //后排序
        while (count>0){
           System.out.println(heap[1]);
           deleteByIndex(1,this.heap,this.count);
           this.count--;
        }
    }

    /*
       堆排序2：原地算法(不借助任何数组)
     */
    private void buildHeap2(int[]array){
        for(int i=(array.length-1)/2;i>0;i--){
            heapify(array, array.length-1, i);
        }
    }

    public void HeapSort2(int[] array){
        //先建堆
        buildHeap2(array);
        //再排序
       int k=array.length-1;
       while (k>1){
           swap(array, 1, k);
           k--;
           heapify(array, k, 1);
       }
    }

    //遍历元素
    public void printAll(){
        for(int i=1;i<=count;i++){
            System.out.println(heap[i]);
        }
    }

    public static void main(String[] args) {
         Heap heap=new Heap(5);

        //Test passed: insert new element of the heap
//        heap.insert(1);
//        heap.insert(2);
//        heap.insert(3);
//        heap.insert(4);
//        heap.insert(5);
//        heap.printAll();

        //Test passed: delete any element of the heap
//        System.out.println("After delete: ");
//        heap.delete(1);
//        heap.printAll();

        //Test passed: the Tree.Heap.Heap sort1 algorithm
//        int[]arr={10,12,9,7,13};
//        heap.HeapSort1(arr);

        //Test passed: the Tree.Heap.Heap sort2 algorithm
          int[]arr={0,10,12,9,7,13};
          heap.HeapSort2(arr);
          for (int i=1;i<arr.length;i++){
              System.out.println(arr[i]);
          }

    }

}
