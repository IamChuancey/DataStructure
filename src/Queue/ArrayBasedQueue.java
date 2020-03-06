package Queue;
/*
队列拥有两个指针，head指针和tail指针，
head指向队首元素，tail指向队尾元素的下一位
*/
public class ArrayBasedQueue {
   private int head=0;
   private int tail=0;
   private int array[];
   public ArrayBasedQueue(int size){
       array=new int[size];
   }
   //入队列
   public void enQueue(int item){
      //如果tail至尾部时
      if(tail==array.length){
         //如果满时，不能再插入元素
          if(head==0){
            System.out.println("The queue is Full!");
            return;
          }
          //如果队列未满时，但tail至尾部，则从head到tail进行数据搬移
          for(int i=head;i<tail;i++){
              array[i-head]=array[i];
          }
          tail=tail-head;
          head=0;
      }
      array[tail]=item;
      tail++;
   }

   //出队列
   public int deQueue(){
       //如果队空时，则不能再出队
       if(head==tail){
           System.out.println("The Queue is empty!");
           return -1;
       }
       return array[head++];
   }

    //打印队列中所有元素
    public void print(){
       for(int i=head;i<tail;i++){
           System.out.println(array[i]);
       }
    }

    public static void main(String[] args) {
          ArrayBasedQueue queue=new ArrayBasedQueue(3);
          System.out.println("入队列：");
          queue.enQueue(1);
          queue.enQueue(2);
          queue.enQueue(3);
          queue.enQueue(4);
          queue.print();

          System.out.println("出队列：");
          queue.deQueue();
          queue.deQueue();
          queue.print();

          System.out.println("再入队列：");
          queue.enQueue(4);
          queue.print();
    }

}
