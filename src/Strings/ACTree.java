package Strings;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.util.LinkedList;
import java.util.Queue;

public class ACTree {
    private ACNode root=new ACNode('/');
    //插入字符串
    public void insert(char[]text){
        ACNode p=root;
        for(int i=0;i<text.length;i++){
            int index=text[i]-'a';
            if(p.children[index]==null){
                ACNode newNode=new ACNode(text[i]);
                p.children[index]=newNode;
                newNode.length=p.length+1;
            }
            p=p.children[index];
        }
        p.isEndingChar=true;
    }
    //查找字符串
    public  boolean find(char[]pattern){
        ACNode p=root;
        for(int i=0;i<pattern.length;i++){
            int index=pattern[i]-'a';
            if(p.children[index]==null){
                return  false;
            }
            p=p.children[index];
        }
        if(p.isEndingChar==false)return false;
        else return true;
    }
    //构建失败指针
    public void buildFailurePointer(){
        Queue<ACNode>queue=new LinkedList<>();
        root.fail=null;
        queue.add(root);
        //使用层级遍历每个节点, 并给每个节点加上失败指针
        while (!queue.isEmpty()){
            ACNode p=queue.poll();
            for(int i=0;i<26;i++){
                ACNode pchild=p.children[i];
                if(pchild==null) continue;
                if(p==root){
                    pchild.fail=root; //root的子节点的fail指针指向root
                }else{
                    ACNode q=p.fail;
                    while (q!=null){
                        ACNode qchild=q.children[pchild.data-'a']; //在其他分支找到了相同的子节点字符
                        if(qchild!=null){
                            pchild.fail=qchild;
                            break;
                        }
                        q=q.fail;
                    }
                    if(q==null){
                        pchild.fail=root;
                    }
                }
                queue.add(pchild);
            }
        }
    }

    //trie树单模式下的find函数，这里为多模式串下的match函数
    public void match(char[]text){ //text是主串
        int n=text.length;
        ACNode p=root;
        for(int i=0;i<n;i++){
            int idx=text[i]-'a';
            while (p.children[idx]==null&&p!=root){ //顺着fail指针继续查找
                p=p.fail; //跳出循环时，p.children[idx]！=null，说明沿着fail指针找到了下一个匹配的字符或者已经到root
            }
            p=p.children[idx];
            if(p==null) p=root; //如果1。root的子节点和第一个字符都没有匹配 2。沿着fail指针已经到root,仍没有匹配，说明此模式串无匹配项，将p归位root，从主串的下个字母开始模式串的匹配
            ACNode tmp=p;
            while (tmp!=root){ //检查是否有匹配的模式串
                if(tmp.isEndingChar==true){
                    int pos=i-tmp.length;
                    System.out.println("匹配起始下标："+pos+"; 长度： "+(tmp.length+1));
                }
                tmp=tmp.fail;
            }
        }
    }

    public static void main(String[] args) {
        ACTree tree=new ACTree();
        char[]a={'h','i',};
        char[]b={'h','e','y'};
        char[]mainStr={'a','h','i','h','e','y'};
        tree.insert(a);
        tree.insert(b);
        tree.buildFailurePointer();
        tree.match(mainStr);
    }
}
