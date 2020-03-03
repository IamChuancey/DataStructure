package Strings;

public class BF {
    public int BruteForce(String mainStr,String patternStr){
        for(int i=0;i<mainStr.length();i++){
            int count=0;
            for(int j=0;j<patternStr.length();j++){
                if(mainStr.charAt(i+j)==patternStr.charAt(j)){
                     count++;
                }else break;
            }
            if(count==patternStr.length()){
                return i;
            }
        }
        return -1;
    }



    public static void main(String[] args) {
        String mainStr="abcdefg";
        String patternStr="def";
        BF bf=new BF();
        int result=bf.BruteForce(mainStr, patternStr);
        System.out.println("找到："+(result==-1?false:result));
    }
}
