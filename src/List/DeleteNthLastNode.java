package List;

public class DeleteNthLastNode {
    //将链表反序
    public Node reverseNode(Node root){
        Node p=root;
        if(root.next==null) return root;
        Node n=root.next;
        while (n.next!=null){
            Node temp=n;
            n=n.next;
            temp.next=p;
            p=temp;
        }
        n.next=p;
        root.next=null;
        return n;
    }

    //删除倒数第n个节点等于将链表反序，删除正数第n个节点，再反序回来
    public Node deleteNthNode(Node root, int index){
        //将链表反序
        Node reverseHead=reverseNode(root);
        //删除正数第n个节点
        int count=1;
        Node head=reverseHead;
        while (count<index){
            reverseHead=reverseHead.next;
        }
        Node First=reverseHead;
        Node Third=reverseHead.next.next;
        First.next=Third;
        //再反序回来
        Node RereverseHead=reverseNode(head);
        return RereverseHead;
    }

    //打印节点
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

       root.next=node1;
       node1.next=node2;
       node2.next=node3;

       DeleteNthLastNode delete=new DeleteNthLastNode();
       System.out.println("原序打印：");
       //原序打印
       delete.print(root);
       //删除倒数第N个节点
       System.out.println("删除倒数第N个节点：");
       Node head=delete.deleteNthNode(root, 1);
       delete.print(head);
    }


}
