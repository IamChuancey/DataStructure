import java.util.*;

public class testArrayDequeue {
    public static void main(String[] args) {
        //底层是循环数组，既可用作顺序栈，也可以用作顺序队列
//        ArrayDeque<Integer>queue=new ArrayDeque<>();
//        queue.addFirst(1);
//        queue.addFirst(2);
//        queue.addFirst(3);
//        queue.removeLast();
//        queue.removeLast();
        LinkedList<Integer>queue=new LinkedList<>();
        queue.addLast(1);
        queue.addLast(2);
        queue.addLast(3);
        queue.removeFirst();
    }
}
