package List;

public class DeleteNthLastNode2 {
    //快慢指针
    public Node deleteNthNode(Node root, int index){
       Node Nfast=root;
       Node slow=root;
       Node head=root;
       int count=0;
       while (count<=index){
           Nfast=Nfast.next;
           count++;
       }

       if(Nfast.next==null) return head.next;

       while (Nfast!=null){
         Nfast=Nfast.next;
         slow=slow.next;
       }

       Node nnext=slow.next.next;
       slow.next=nnext;

       return head;
    }

    public void print(Node root){
        while (root!=null){
            System.out.println(root.val);
            root=root.next;
        }
    }

    public static void main(String[] args) {
       Node root=new Node(1);
       Node node1=new Node(2);
       Node node2=new Node(3);
       Node node3=new Node(4);
       Node node4=new Node(5);

       root.next=node1;
       node1.next=node2;
       node2.next=node3;
       node3.next=node4;

       DeleteNthLastNode2 delete=new DeleteNthLastNode2();
       Node head=delete.deleteNthNode(root, 5);
       delete.print(head);
    }
}
