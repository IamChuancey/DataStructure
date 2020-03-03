package List.LinkedLIst;

public class LinkedList { //双向链表
    private Dnode head;
    private Dnode tail;
    private  int size=0;
    public void add(Dnode node){
        if(head==null){
            head=node;
            tail=head;
        }else{
            tail.next=node;
            node.prev=tail;
            tail=tail.next;
        }
       size++;
    }

    //模拟队列：先进先出
    public void pull(){
       head=head.next;
       size--;
    }

    //模拟栈：先进后出
    public void pop(){
        tail=tail.prev;
        size--;
    }

    public static void main(String[] args) {
       Dnode node1=new Dnode(1);
       Dnode node2=new Dnode(2);
       Dnode node3=new Dnode(3);
       Dnode node4=new Dnode(4);
       LinkedList list=new LinkedList();
       list.add(node1);
       list.add(node2);
       list.add(node3);
       list.add(node4);
    }


}
