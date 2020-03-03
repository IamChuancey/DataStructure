package Strings;

public class Trie {
   private TrieNode root=new TrieNode('/');
   //插入字符串
   public void insert(char[]text){
      TrieNode p=root;
      for(int i=0;i<text.length;i++){
         int index=text[i]-'a';
         if(p.children[index]==null){
            TrieNode newNode=new TrieNode(text[i]);
            p.children[index]=newNode;
         }
         p=p.children[index];
      }
      p.isEndingChar=true;
   }
   //查找字符串
   public  boolean find(char[]pattern){
      TrieNode p=root;
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

   public static void main(String[] args) {
      Trie trie=new Trie();
      char[]a={'h','e','l','l','o'};
      char[]b={'h','i'};
      char[]c={'h','e'};
      char[]d={'s','o'};
      trie.insert(a);
      trie.insert(b);
      trie.insert(c);
      trie.insert(d);
      System.out.println(trie.find(c));
   }

}
