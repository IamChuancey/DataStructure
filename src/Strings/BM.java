package Strings;

public class BM {
    private static final int SIZE=256;
    /*
       b:模式串数组，
       m:模式串的长度，
       bc:模式串的散列表，散列表的下标是字符的ascii值，散列表存储字符
    */
    private void generateBC(char[]b,int m, int[]bc){
        for(int i=0;i<SIZE;i++){
            bc[i]=-1;
        }
        for(int i=0;i<m;i++){
            int ascii=(int)b[i]; //计算b[i]的ascii值
            bc[ascii]=i;
        }
    }

    /*
       a:主串
       n:主串的长度
       b:模式串
       m:模式串的长度
     */
    public int bm(char[]a,int n,char[]b,int m){
        int[]bc=new int[SIZE];
        generateBC(b,m,bc); //坏字符原则
        int[]suffix=new int[m];
        boolean[]prefix=new boolean[m];
        generateGS(b,m,suffix,prefix); //好后缀原则
        int i=0;
        while (i<=n-m){
           int j;
           for(j=m-1;j>=0;j--){ // 模式串从后往前匹配
               if(a[i+j]!=b[j]) break; // 坏字符
           }
           if(j<0){ //从后往前匹配的过程中没出现坏字符，说明匹配成功，并返回主串与模式串第一个匹配的字符的位置
               return i;
           }
           /*
              不匹配时，将模式串往后滑动j-bc[(int)a[i+j]]位:
              j:坏字符对应的模式串中的字符下标
              a[i+j]:坏字符
              bc[(int)a[i+j]]：坏字符在模式串中的下标
            */
           int x=(j-bc[(int)a[i+j]]); //根据坏字符原则需要移动的距离
           int y=0;
           if(j<m-1){ //如果有好后缀
               y=moveByGS(j,m,suffix,prefix);
           }
           i=i+Math.max(x,y);
        }
        return -1;
    }

    /*
      b:模式串
      m:模式串的长度
      suffix:后缀子串
      prefix:前缀子串
     */
    private void generateGS(char[]b,int m,int[]suffix,boolean[]prefix){
        for(int i=0;i<m;i++){ //初始化
            suffix[i]=-1;
            prefix[i]=false;
        }
        for(int i=0;i<m-1;i++){
            int j=i;
            int k=0; //公共后缀子串长度
            while (j>=0&&b[j]==b[m-1-k]){
                j--;
                k++;
                suffix[k]=j+1;//j+1表示公共后缀子串在b[0, i]中的起始下标
            }
            if(j==-1) prefix[k]=true;
        }
    }

    //计算根据好后缀原则需要移动的距离
    private int moveByGS(int j,int m,int[]suffix,boolean[]prefix){
        int k=m-1-j; //好后缀的长度
        if(suffix[k]!=-1) return j-suffix[k]+1; //好后缀在模式串中有匹配的子串
        for(int r=j+2;r<m-1;r++){
            if(prefix[m-r]==true){ //好后缀在模式串中无匹配的子串，但好后缀的后缀子串[r,m-1]与模式串的前缀子串有匹配的
                return r;
            }
        }
        return m; //都没有匹配，则直接移动一个模式串的长度
    }

    public static void main(String[] args) {
        char a[]={'c','a','b','c','a','b'};
        char b[]={'c','a'};
        BM bmI=new BM();
        System.out.println(bmI.bm(a,a.length,b,b.length));
    }

}
