package Queue.CircularQueue;

import Queue.LinkedListBasedQueue;
import Queue.Node;

//链式循环队列：单链表
public class LinkedListBasedCircularQueue {

   private Node head;
   private Node tail;
   //哨兵节点
   protected Node root;
   public LinkedListBasedCircularQueue(){
      this.root=new Node(-1);
      head=root.next;
      tail=root;
   }

   //出队，删除节点,存在队空的情况
   public void deQueue(){
      if(root.next==null){
         System.out.println("The queue is Empty!");
         return;
      }
      head=head.next;
      root.next=head;
   }

   //入队，插入节点,无队满的情况
    public void enQueue(int value){
       Node node=new Node(value);
       tail.next=node;
       tail=tail.next;
       //成环
       tail.next=root;
    }

    //打印全队列
    public void print(){
        Node pointer=head;
        while (tail.next!=pointer){
           System.out.println(pointer.val);
           pointer.next=pointer;
        }
    }

   public static void main(String[] args) {
      LinkedListBasedQueue queue=new LinkedListBasedQueue();
      queue.enQueue(1);
      queue.enQueue(2);
      queue.enQueue(3);
      System.out.println("入队：");
      queue.print();
      System.out.println("出队：");
      queue.deQueue();
      queue.print();
      System.out.println("出队：");
      queue.deQueue();
      queue.print();
      System.out.println("出队：");
      queue.deQueue();
      queue.print();
   }


}
