package Stack;//在实现的时候，不应该把指针暴露出来，而我这里暴露了出来

public class LinkedListbasedStack {
    protected Node root;
    protected Node Top;
    public LinkedListbasedStack(){
        this.root=new Node(-1);
        Top=root;
    }

    public void push(int value){
        Node node=new Node(value);
        Top.next=node;
        Top=Top.next;
    }

    public int pop(){
        if(root.next==null){
            System.out.println("Stack is Empty!");
            return -1;
        }
        Node pointer=root;
        while (pointer.next!=Top){
            pointer=pointer.next;
        }
        pointer.next=null;
        Node temp=Top;
        Top=pointer;
        return temp.val;
    }

    public void print(){
        if(Top==root){
            System.out.println("the Stack has been Empty!");
            return;
        }
        Node pointer=root.next;
        while (Top.next!=pointer){
          System.out.println(pointer.val);
          pointer=pointer.next;
        }
    }

    public static void main(String[] args) {
        LinkedListbasedStack stack=new LinkedListbasedStack();
        System.out.println("入栈：");
        stack.push(1);
        System.out.println(stack.Top.val);
        stack.push(2);
        System.out.println(stack.Top.val);
        stack.push(3);
        System.out.println(stack.Top.val);
        System.out.println("打印栈内所有元素：");
        stack.print();

        System.out.println("出栈：");
        stack.pop();
        System.out.println(stack.Top.val);
        System.out.println("打印栈内所有元素：");
        stack.print();
        stack.pop();
        System.out.println(stack.Top.val);
        System.out.println("打印栈内所有元素：");
        stack.print();
        stack.pop();
        System.out.println(stack.Top.val);
        System.out.println("打印栈内所有元素：");
        stack.print();
    }


}
