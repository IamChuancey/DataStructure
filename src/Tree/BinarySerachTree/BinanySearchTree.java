package Tree.BinarySerachTree;

public class BinanySearchTree {
   //二叉搜索树的根节点
   private BSTNode root;

   //插入节点，不考虑插入指定位置
   public void insert(int key){
      BSTNode p=new BSTNode(key); //待插入节点
      if(root==null){
          root=p;
      }else{
           //不考虑相等的情况
           BSTNode current=root;
           BSTNode parent;
           while (true){
               parent=current;
               if(key>current.value){
                   current=current.right;
                   if(current==null){
                       parent.right=p;
                       return;
                   }
               }
               if(key<current.value){
                   current=current.left;
                   if(current==null){
                       parent.left=p;
                       return;
                   }
               }
           }
      }
   }

   //删除节点，删除指定位置的节点
   //删除节点因为要调整树的结构，需要分情况讨论：1.删除的节点是叶子节点 2.删除的节点有一个子节点 3.删除的节点有两个子节点(这种情况需要找到待删节点的中序后继节点)
   //删除节点后，要从被删除节点的左子树或右子树中选出一个节点顶替原节点的位置，选左子树还是右子树都可以，这里我们选右子树。
   public void delete(int key){
      BSTNode current=root;
      BSTNode parent=current;
      boolean isRightChild=true;
      //查找相应的节点（可封装成单独的函数）
      while (current.value!=key){
         parent=current;
         if(key>current.value){
             current=current.right;
             isRightChild=true;
             return;
         }else {
             current=current.left;
             isRightChild=false;
             return;
         }
      }
      //此时current就是要删除的节点，parent为其父节点，下面开始删除节点
      //1.要删除的节点是叶子节点
      if(current.right==null&&current.left==null){
          if(current==root){
              root=null; //清空整棵树
          }else{
              if(isRightChild){
                  //如果是右叶子节点
                  parent.right=null;
              }else {
                  //如果是左叶子节点
                  parent.left=null;
              }
          }
      }
      //2.要删除的节点只有一个子节点
      else if(current.left==null){
          if(current==root){
              root=current.right;
          }else if(isRightChild){
              parent.right=current.right;
          }else {
              parent.left=current.right;
          }
      }
      else if(current.right==null){
          if(current==root){
              root=current.left;
          }else if(isRightChild){
              parent.right=current.left;
          }else{
              parent.left=current.left;
          }
      }
      //3.要删除的节点有两个子节点
      else{
          BSTNode successor=getSuccessor(current);
          if(current==root){
              root=successor;
          }else if(isRightChild){
              parent.right=successor;
          }else {
              parent.left=successor;
          }
          successor.left=current.left;
      }
   }

   //寻找待删除节点的后继节点
   //寻找方法一：后继节点的左子树为空（也是此函数采用的方法）
   //寻找方法二；后继节点是右子树的最小的节点。（这是最常用的方法）
   public BSTNode getSuccessor(BSTNode delNode){
       BSTNode successorParent=delNode.right;
       BSTNode pointer=successorParent;
       BSTNode successor=null; //初始化赋为null
       //用来寻找中序后继节点
       while (pointer!=null){
           successor=pointer;
           pointer=pointer.left;
       }
       //如果待删除节点不的successor不是待删除节点的直接右子节点这种简单的情况，需要预先调整待删除节点的右子树
       if(successor!=delNode.right){
           successorParent.left=successor.right;
           successor.right=delNode.right;
       }
       return successor;
   }

   public void preOrder(BSTNode root){
       if(root!=null){
           System.out.println(root.value);
           preOrder(root.left);
           preOrder(root.right);
       }
   }

}
