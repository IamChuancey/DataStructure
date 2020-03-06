package Queue.CircularQueue;
/*
   数组实现的双端队列:本质上是循环队列
   head指向队首元素，tail指向队尾元素的下一位
 */
public class ArrayBasedCircularQueue {
    private int head=0;
    private int tail=0;
    protected int[] array;
    //循环队列的大小要比实际的要大1
    public ArrayBasedCircularQueue(int capacity){
        array=new int[capacity+1];
    }

    //入队
    public void enQueue(int item){
        //判断是否队满
       if((tail+1)%(array.length)==head){
          System.out.println("The queue is Full!");
          return;
        }
        array[tail]=item;
        tail=(tail+1)%(array.length);
    }

    //出队
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

    //打印队列所有元素
    public void print(){
       for (int i = head; i % array.length != tail; i = (i + 1) % array.length){
           System.out.println(array[i]);
       }
    }

    public static void main(String[] args) {
        ArrayBasedCircularQueue queue=new ArrayBasedCircularQueue(3);
        queue.enQueue(1);
        queue.enQueue(2);
        queue.enQueue(3);
        //队满
        queue.enQueue(4);
        queue.print();

        //出队
        System.out.println("出队：");
        queue.deQueue();
        queue.print();

        //入队
        System.out.println("入队：");
        queue.enQueue(4);
        queue.print();

        //出队
        queue.deQueue();

        //入队
        System.out.println("入队：");
        queue.enQueue(5);
        queue.print();

    }

}
