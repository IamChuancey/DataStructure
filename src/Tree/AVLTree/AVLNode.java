package Tree.AVLTree;

public class AVLNode {
   public  AVLNode left;
   public AVLNode right;
   public  int height; //树的高度
   public int value; //树的值
   public  AVLNode(int val){
      this.value=val;
      this.height=1;
   }
}
