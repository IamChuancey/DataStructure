package List;
//快慢指针求中间节点
public class FindTheMiddleNode {
    public void FindMiddleNode(Node root){
        Node slowpointer=root;
        Node fastpointer=root;
        Node Temp=root;
        try {
            while (fastpointer!=null){
                Temp=slowpointer;
                slowpointer=slowpointer.next;
                fastpointer=fastpointer.next.next;
            }
        }catch (NullPointerException e){

        }finally {
            System.out.println("Middle Node Value: "+Temp.val);
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

        FindTheMiddleNode find=new FindTheMiddleNode();
        find.FindMiddleNode(root);
    }
}
