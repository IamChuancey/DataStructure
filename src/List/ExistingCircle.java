package List;

public class ExistingCircle {
    public boolean ExistingCircleJudge(Node root){
        Node slow=root;
        Node fast=root;
        boolean flag=false;
        while (fast.next!=slow||fast.next!=null){
            slow=slow.next;
            fast=fast.next.next;
            if(slow==fast){
                flag=true;
                break;
            }
        }
        return flag;
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
        //存在环
        node4.next=node1;

        ExistingCircle exist=new ExistingCircle();
        System.out.println(exist.ExistingCircleJudge(root));

    }
}
