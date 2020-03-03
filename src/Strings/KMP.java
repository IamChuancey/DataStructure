package Strings;

public class KMP {
    /*
      a:主串
      n:主串的长度
      b:模式串
      m:模式串的长度
     */
    public  int kmp(char[]a,int n,char[]b, int m){
        int[]next=generateNext(b,m);
        int j=0;
        for(int i=0;i<n;i++){
            while (j>0&&a[i]!=b[j]){
                j=next[j-1]+1;  //j=最长可匹配前缀子串的结尾字符的后一个
            }
            if(a[i]==b[j]){ //好前缀
                j++;
            }
            if(j==m){ //找到匹配模式串的了
                return i-m+1;
            }
        }
        return -1;
    }

    //生成next数组
    public int[]generateNext(char[] b, int m){
        int[]next=new int[m];
        next[0]=-1;
        int k=-1; //最长可匹配前缀子串的结尾字符的下标
        for(int i=1;i<m;i++){ //好前缀是b[0,i],i的取值范围是[1,m-1]
            while (k!=-1&&b[k+1]!=b[i]){
                k=next[k]; //这步操作值得深入分析
            }
            if(b[k+1]==b[i]){
                k++;
            }
            next[i]=k; //k是最长可匹配前缀子串的结尾字符的下标
        }
        return next;
    }

    public static void main(String[] args) {
        char a[]={'a','b','a','b','a','c','d'};
        char b[]={'a','c'};
        KMP kmp=new KMP();
        System.out.println(kmp.kmp(a,a.length,b,b.length));
//        int next[]=kmp.generateNext(b,b.length);
//        for(int i=0;i<next.length;i++){
//            System.out.println(next[i]);
//        }
    }
}
