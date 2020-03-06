package Queue.Deque;
/*
   数组实现的双端队列:本质上是循环队列
   head指向队首元素，tail指向队尾元素的下一位
 */
public class ArrayBasedDeque {
    private int head=0;
    private int tail=0;
    protected int[] array;

    //循环队列的大小要比实际的要大1
    public ArrayBasedDeque(int capacity){
        array=new int[capacity+1];
    }

    //入队：从前往后 -->
    /*
     这在ArrayBasedDeque里称之为addLast();
     */
    public void enQueue(int item){
        //判断是否队满
        if((tail+1)%(array.length)==head){
            System.out.println("The queue is Full!");
            return;
        }
        array[tail]=item;
        tail=(tail+1)%(array.length);
    }

    //入队：从后往前 <--
    public void AddFirst(int item){
       if((tail+1)%(array.length)==head){
           System.out.println("The queue is Full!");
           return;
       }
       array[head=(head - 1) & (array.length - 1)]=item;
    }

    //出队:先进先出
    /*
     这在ArrayBasedDeque里称之为removeFirst();
     */
    public int deQueue(){
        //判断是否队空
        if(tail==head){
            System.out.println("The queue is Empty!");
            return -1;
        }
        int temp=head;
        head=(head+1)%(array.length);
        return array[temp];
    }

    //出队：先进先出
    public int removeLast(){
        if(tail==head){
            System.out.println("The queue is Empty!");
            return -1;
        }
        return array[tail=(tail-1)& (array.length - 1)];
    }

    //打印队列所有元素
    public void print(){
        for (int i = head; i % array.length != tail; i = (i + 1) % array.length){
            System.out.println(array[i]);
        }
    }

    public static void main(String[] args) {
        ArrayBasedDeque queue=new ArrayBasedDeque(3);
        /*
          test enqueue
         */
//        queue.enQueue(1);
//        queue.enQueue(2);
//        queue.enQueue(3);
//
//        queue.print();

        /*
         test addFirst
        */
         queue.AddFirst(1);
         queue.AddFirst(2);
         queue.AddFirst(3);

         queue.AddFirst(4);
         queue.print();

         /*
         test removeLast
          */
         System.out.println("After remove the last element.");
         queue.removeLast();
         queue.print();
         System.out.println();
         queue.removeLast();
         queue.print();
         System.out.println();
         queue.removeLast();
         //the queue is empty.
         queue.removeLast();

    }

}
