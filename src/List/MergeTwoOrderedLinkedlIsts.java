package List;

public class MergeTwoOrderedLinkedlIsts {
    //升序处理
    public Node MergeTwoOrderedLinkedlIsts(Node root1, Node root2){
        //头节点
        Node root=new Node(-1);
        //头节点的备份
        Node head=root;
        //大小指针，大指针不动，小指针动
        Node little=root1,bigger=root2;
        while (little!=null&&bigger!=null){
           if(little.val<=bigger.val){
               root.next=little;
               little=little.next;
           }else {
               root.next=bigger;
               bigger=bigger.next;
           }
           root=root.next;
        }
        //如果链表长度不一致，处理每个链表剩余的不一致的部分
        while (little!=null){
            root.next=little;
            little=little.next;
            root=root.next;
        }
        while (bigger!=null){
            root.next=bigger;
            bigger=bigger.next;
            root=root.next;
        }
        return head.next;
    }

    public void print(Node root){
        while (root!=null){
            System.out.println(root.val);
            root=root.next;
        }
    }

    public static void main(String[] args) {
        Node root1=new Node(1);
        Node node1=new Node(3);
        Node node2=new Node(4);

        Node root2=new Node(2);
        Node node3=new Node(5);
        Node node4=new Node(7);

        root1.next=node1;
        node1.next=node2;

        root2.next=node3;
        node3.next=node4;

        MergeTwoOrderedLinkedlIsts merge=new MergeTwoOrderedLinkedlIsts();
        Node root=merge.MergeTwoOrderedLinkedlIsts(root1, root2);
        merge.print(root);

    }
}
