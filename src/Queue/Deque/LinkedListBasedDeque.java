package Queue.Deque;

/*
   双向链表实现:这里tail指向队尾元素的下一个空位，但应指向队尾元素,
   这里使用了privousTail代替tail，head指向队首元素。
 */
public class LinkedListBasedDeque {
   //节点类
   private class LinkedNode{
       private LinkedNode prev;
       private LinkedNode next;
       private int val;
       public LinkedNode(int value){
           this.val=value;
       }
   }
   private LinkedNode head;
   private LinkedNode tail; //tail指针指向队尾元素队下一个空位
   private LinkedNode privousTail; //临时指针，在addLast中使用
   private int size=0;
   /*
    addLast和removeFirst搭配实现队列
   */
   public void addLast(int value){
       LinkedNode node=new LinkedNode(value);
       if(head==null){
           head=node;
       }
       if(tail==null){
           node.prev=privousTail;
           tail=node;
           if(privousTail!=null) privousTail.next=tail;
       }
       privousTail=tail;
       tail=tail.next;
       size++;
   }

   public void removeFirst(){
       if(head==tail){
           System.out.println("队已空！");
           return;
       }
       head=head.next;
       size--;
   }

   /*
    addFirst和removeLast搭配实现队列
    */
   public void addFirst(int value){
       LinkedNode node=new LinkedNode(value);
       if(head==null){
           head=node;
           privousTail=head;
       }else {
           head.prev=node;
       }
       if(head!=node) node.next=head;
       if(head.prev!=null)head=head.prev;
       size++;
   }

   public void removeLast(){
       if(head==tail){
           System.out.println("队已空！");
           return;
       }
       privousTail=privousTail.prev;
       size--;
   }

   public void print(){
       LinkedNode node=head;
       if(size==0){
           System.out.println("队为空！");
       }
       while (privousTail!=null&&node!=privousTail.next){
           System.out.print(node.val);
           node=node.next;
       }
       System.out.println();
   }


    public static void main(String[] args) {
       //test1: addLast和removeFirst搭配实现队列
//         LinkedListBasedDeque deque=new LinkedListBasedDeque();
//         System.out.println("Test addLast():");
//         deque.addLast(1);
//         deque.addLast(2);
//         deque.addLast(3);
//         deque.print();
//         System.out.println("Test removeFirst():");
//         deque.removeFirst();
//         deque.print();
//         deque.removeFirst();
//         deque.print();
//         deque.removeFirst();
//         deque.print();
       //test1: addLast和removeFirst test passed

       //test2: addFirst和removeLast
//       deque.addFirst(1);
//       deque.addFirst(2);
//       deque.addFirst(3);
//       deque.print();
//       deque.removeLast();
//       deque.print();
//       deque.removeLast();
//       deque.print();
//       deque.removeLast();
//       deque.print();
       //test2: addFirst和removeLast test passed
    }

}
