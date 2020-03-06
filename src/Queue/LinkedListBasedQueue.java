package Queue;

//单链表实现的队列
public class LinkedListBasedQueue {
   private Node head;
   private Node tail;
   //哨兵节点
   protected Node root;
   public LinkedListBasedQueue(){
       //哨兵节点
       root=new Node(-1);
       head=root;
       tail=root;
   }

   //入队列的操作，不需要考虑队满的情况，因为链式队列没有长度限制，
   public void enQueue(int value){
       Node item=new Node(value);
       tail.next=item;
       tail=tail.next;
   }

   //出队列的操作，需要考虑队空的情况，如果队空，则重新初始化
   public void deQueue(){
       head=head.next;
       //如果队列为空时,不能再插入元素，并且要重新初始化。
       if(head==tail){
           System.out.println("The queue is empty！ ");
           head=root;
           tail=root;
       }
   }

   public void print(){
       Node pointer=head;
       while (pointer!=tail){
           System.out.println(pointer.next.val);
           pointer=pointer.next;
       }
   }

    public static void main(String[] args) {
        LinkedListBasedQueue queue=new LinkedListBasedQueue();
        queue.enQueue(1);
        queue.deQueue();

        queue.enQueue(1);
        queue.enQueue(2);
        queue.deQueue();
        queue.print();

        queue.deQueue();
        queue.enQueue(3);
        queue.enQueue(4);
        queue.print();

    }


}
