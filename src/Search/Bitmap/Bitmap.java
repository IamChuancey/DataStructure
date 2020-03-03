package Search.Bitmap;

public class Bitmap {
    private char[]bytes; //java中一个char占16bit,因此我们用char数组表示bit数组，数组长度只须为原来的16分之一（再加1）
    private int nbits;
    public Bitmap(int nbits){
        this.nbits=nbits;
        this.bytes=new char[nbits/16+1];//16bit分别表示0～15是否存在
    }
    public void set(int k){ //为整数k寻找其对应的下标
        if(k>nbits) return;
        int byteIndex=k/16;
        int bitIndex=k%16;
        bitIndex=(1<< bitIndex);
        bytes[byteIndex] |=bitIndex; //第byteIndex上的bitIndex位是相应数字的标志位，将所有这样的标志位都表示出来
    }
    public boolean get(int k){
        if(k>nbits) return false;
        int byteIndex=k/16;
        int bitIndex=k%16;
        bitIndex=1<<bitIndex;
        return (bytes[byteIndex]&bitIndex)!=0; ////第byteIndex上的bitIndex位存储相应的数字是否存在(是否为1)
    }

    public static void main(String[] args) {
        //假设我们有17个数字，我们就用17个二进制位表示这17个数字是否已存在
        Bitmap map=new Bitmap(17);
        map.set(1);
        map.set(5);
        System.out.println(map.get(5));
    }

}
