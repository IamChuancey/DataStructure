package Tree.RBTree;

public class RBTNode {
    int value;
    RBTNode left,right;
    boolean color;
    public  RBTNode(int value,boolean color){
        this.value=value;
        this.color=color;
    }
}
