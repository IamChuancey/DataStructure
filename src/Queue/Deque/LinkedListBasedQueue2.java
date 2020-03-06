package Queue.Deque;

/*
   双向链表的标准实现:tail指向队尾元素,head指向队首元素。
 */
public class LinkedListBasedQueue2 {
    //节点类
    private class Node{
        private Node prev;
        private Node next;
        private int val;
        /*
         相比于LinkedListBasedQueue，构造函数的改变大大简化了代码的实现。
         */
        public Node(Node prev, int value, Node next){
            this.prev=prev;
            this.val=value;
            this.next=next;
        }
    }

    private int size=0;
    private Node head;
    private Node tail;

    /*
    addLast和removeFirst搭配实现队列
    */
    public void addLast(int value){
        Node l = tail;
        Node newNode = new Node(l, value, null);
        tail = newNode;
        if (l == null)
            head = newNode;
        else
            l.next = newNode;
        size++;
    }

    public void removeFirst(){
       if(this.size<=0){
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
        Node h = head;
        Node newNode = new Node(null, value, h);
        head=newNode;
        if(h==null){
            tail=newNode;
        }else {
            h.prev=newNode;
        }
        size++;
    }

    public void removeLast(){
        if(size<=0){
            System.out.println("队空！");
            return;
        }
        tail=tail.prev;
        size--;
    }

    public void print(){
        if(size<=0) {
            System.out.println("队空！");
            return;
        }
        Node current=head;
        int i=0;
        while (i<size){
            System.out.print(current.val);
            current=current.next;
            i++;
        }
        System.out.println();
    }




    public static void main(String[] args) {
       /*
         test1: test addLast() and removeFirst() to implement queue
       */
       LinkedListBasedQueue2 queue2=new LinkedListBasedQueue2();
//       queue2.addLast(1);
//       queue2.addLast(2);
//       queue2.addLast(3);
//       queue2.print();
//       System.out.println("now remove last element:");
//       queue2.removeFirst();
//       queue2.print();
//       queue2.removeFirst();
//       queue2.print();
//       queue2.removeFirst();
//       queue2.print();
       /*
         test1 passed
       */

       /*
          test2: test addFirst() and removeLast() to implement queue
        */
//        queue2.addFirst(1);
//        queue2.addFirst(2);
//        queue2.addFirst(3);
//        queue2.print();
//        queue2.removeLast();
//        queue2.print();
//        queue2.removeLast();
//        queue2.print();
//        queue2.removeLast();
//        queue2.print();
        /*
          test2 passed
        */

        /*
        test3: addLast() and removeLast() to simulate stack
         */
//         queue2.addFirst(1);
//         queue2.addLast(2);
//         queue2.addLast(3);
//         queue2.removeLast();
//         queue2.print();
//         queue2.removeLast();
//         queue2.print();
//         queue2.removeLast();
//         queue2.print();
         /*
         test3 passed
         */

         /*
         test4:addFirst() and removeFirst() to simulate stack
         */
         queue2.addFirst(1);
         queue2.addFirst(2);
         queue2.addFirst(3);
         queue2.removeFirst();
         queue2.print();
         queue2.removeFirst();
         queue2.print();
         queue2.removeFirst();
         queue2.print();
         /*
         test4 passed.
          */
    }

}
