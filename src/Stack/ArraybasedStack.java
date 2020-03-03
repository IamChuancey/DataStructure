package Stack;

//指针始终应该指向栈首元素，而且指针不应该暴露出来
public class ArraybasedStack {
   private int[]arr;
   private int top;
   public ArraybasedStack(int capacity){
       arr=new int[capacity];
       top=0;
   }

   //考虑栈满的情况
   public void push(int item){
       if(top>=arr.length){
           System.out.println("The stack is Full!");
           return;
       }
       arr[top]=item;
       top++;
   }

   //考虑队空的情况
   public int pop(){
       if(top<0){
         System.out.println("The stack is Empty!");
         return -1;
       }
       top--;
       if(top<0) return -1;
       return arr[top];
   }

   public void print(){
       if(top<0) {
           System.out.println("The stack has been Empty!");
           return;
       }
       for(int i=0;i<top;i++){
           System.out.println(arr[i]);
       }
   }

    public static void main(String[] args) {
        ArraybasedStack stack=new ArraybasedStack(3);
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.print();

        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        stack.pop();
        stack.print();
    }





}
