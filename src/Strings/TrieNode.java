package Strings;

public class TrieNode {
    char data;
    boolean isEndingChar=false;
    TrieNode children[]=new TrieNode[26];//字符集只包含a~z这26个字符
    public TrieNode(char data){
        this.data=data;
    }
}
