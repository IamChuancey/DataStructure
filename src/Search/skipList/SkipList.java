package Search.skipList;

import java.util.Random;

public class SkipList<T> {
    public int n; //节点数
    public int h;//高度
    private SkipListNode head; //head指向最顶层（top level）链表的开始节点
    private SkipListNode tail; //tail指向最顶层（top level）链表的结尾节点
    //生成randomLevel用到的概率值
    private Random r;

    public SkipList(){
        head=new SkipListNode(Integer.MIN_VALUE, Integer.MIN_VALUE);
        tail=new SkipListNode(Integer.MAX_VALUE,Integer.MAX_VALUE);
        head.right=tail;
        tail.left=head;
        n=0;
        h=0;
        r=new Random();
    }

    public SkipListNode findNode(Integer key){
        SkipListNode p;
        p=head; //start at head
        while (true){
            /* --------------------------------------------
  	   Search RIGHT until you find a LARGER entry
             E.g.: k = 34
                       10 ---> 20 ---> 30 ---> 40
                                        ^
                                        |
                                        p stops here
  		p.right.key = 40
  	   -------------------------------------------- */
         while (p.right.key!=Integer.MAX_VALUE&&p.right.key<= key){
             p = p.right;
         }
         /* ---------------------------------
  	   Go down one level if you can...
  	   --------------------------------- */
         if(p.down!=null){
             p=p.down;
         }else break; // We reached the LOWEST level... Exit...
        }
        return p; // p.key <= k
    }

    public Integer get(int key){
        SkipListNode p;
        p=findNode(key);
        if(p.key==key){
            return p.value;
        }else  return null;
    }

    public Integer insert(int key,int value){
        SkipListNode p,q;
        int i=0; //层数
        //查找适合插入的位置(最底层)
        p=findNode(key);
        //如果跳表中存在含有key值的节点，则更新key对应的value值
        if(p.key==key){
            Integer oldValue=p.value;
            p.value=value;
            return oldValue;
        }
        //如果跳表中不存在含有key值的节点，则进行新增操作
        q=new SkipListNode(key,value);
         /* --------------------------------------------------------------
        Insert q into the lowest level after SkipListNode p:
                         p   put q here           p        q
                         |     |                  |        |
	 	                 V     V                  V        V
        Lower level:    [ ] <------> [ ]    ==>  [ ] <--> [ ] <--> [ ]
        --------------------------------------------------------------- */
        q.left=p;
        q.right=p.right;
        p.right.left=q;
        p.right=q;
        /*
          最底层操作完成，使用随机函数决定此节点是否作为索引插入到上层
         */
        while (r.nextDouble()<0.5 /*抛硬币*/){
            if(i>=h){ //we reached the top level
                //create a new empty TOP layer
                addEmptyLevel();
            }
          /* ------------------------------------
            向左找到第一个up不为空的节点
          ------------------------------------ */
            while (p.up==null){
                p=p.left;
            }
            //将p指向对应的上一层的节点
            p=p.up;
        /* ---------------------------------------------------
          新建一个节点
          Add one more (k,*) to the column
	      Schema for making the linkage:
          p <--> e(k,*) <--> p.right
		           ↓
		           q
	   ---------------------------------------------------- */
            SkipListNode e=new SkipListNode(key, null); //这里需要注意的是除底层节点之外的节点对象是不需要value值的
            e.left=p;
            e.right=p.right;
            e.down=q;
            /*
             change the neighboring links...
            */
            p.right.left=e;
            p.right=e;
            q.up=e;
            //本层插入节点完成后，将e赋给q，再上一层继续处理
            q=e;
            i=i+1;
        }
        n=n+1; //更新最底层链表长度
        return null;
    }

    private void addEmptyLevel(){
        SkipListNode p1,p2;
        p1=new SkipListNode(Integer.MIN_VALUE,null);
        p2=new SkipListNode(Integer.MAX_VALUE,null);
        p1.right=p2;
        p1.down=head;

        p2.left=p1;
        p2.down=tail;

        head.up=p1;
        tail.up=p2;

        head=p1;
        tail=p2;

        h=h+1;
    }

    public Integer remove(int key){
        SkipListNode p,q;
        p=findNode(key); //在最底层找到这个节点
        if(!p.key.equals(key)){
            return null;
        }
        /*
          在最底层找到节点后，如果上一层需要删除节点，则处理上一层
                               q
                               ↓
                               p
	 	                       ↓
             level:   [ ] <--> [ ] <--> [ ] ==> [ ] <------> [ ]
         */
        Integer oldValue=p.value;
        while (p!=null){
           q=p.up;
           p.left.right=p.right;
           p.right.left=p.left;
           p=q;
        }
        return oldValue;
    }

    public static void main(String[] args) {
        SkipList<Integer> skipList=new SkipList<>();
        skipList.insert(1,1);
        skipList.insert(2,2);
        skipList.insert(3,3);
        skipList.insert(4,4);
        skipList.insert(5,5);
        skipList.insert(6,6);
        System.out.println(skipList.findNode(3));
    }




}
