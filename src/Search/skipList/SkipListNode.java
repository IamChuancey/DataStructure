package Search.skipList;

public class SkipListNode {
    Integer key;
    Integer value;
    SkipListNode left;
    SkipListNode right;
    SkipListNode down;
    SkipListNode up;
    public SkipListNode(Integer key,Integer value){
        this.key=key;
        this.value=value;
    }
    public String toString(){
        return "("+key+","+value+")";
    }
}
