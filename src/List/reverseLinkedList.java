package List;

import List.LinkedLIst.LinkedList;

public class reverseLinkedList {
    public Node reverseTheLinkedList(Node root){
        Node n=root.next;
        Node p=root;
        Node temp;

        while (n!=null){
           temp=n.next;
           n.next=p;
           p=n;
           n=temp;
        }

        root.next=n;

        return p;
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

        root.next=node1;
        node1.next=node2;
        node2.next=node3;

        reverseLinkedList reverse=new reverseLinkedList();
        System.out.println("before the reverse:");
        reverse.print(root);

        Node head=reverse.reverseTheLinkedList(root);
        System.out.println("reverse the linklist:");
        reverse.print(head);
    }
}
