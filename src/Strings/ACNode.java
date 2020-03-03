package Strings;

public class ACNode {
    public  char data;
    public ACNode[] children=new ACNode[26]; //字符集只包含a~z这26个字符
    public boolean isEndingChar=false;
    public int length=-1; //当isEndingChar=true时，记录模式串的长度
    public ACNode fail; //失败指针
    public ACNode(char data){
        this.data=data;
    }
}
