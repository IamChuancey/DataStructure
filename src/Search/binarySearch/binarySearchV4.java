package Search.binarySearch;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class binarySearchV4 {
    /*
   数组是有序的但是存在重复元素
   目标：找出最后一个小于等于给定值的元素
  */
    public int binarySearch(int[]arr,int target){
        int low=0;
        int high=arr.length-1;
        while (low<high){
            int mid=(low+high)/2;
            if(arr[mid]<=target){
                if(mid==(arr.length-1)||arr[mid+1]>target)
                    return mid;
                low=mid;
            }
            if(arr[mid]>target){
                high=mid;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int arr[]={1,5,5,5,9};
        binarySearchV4 v4=new binarySearchV4();
        System.out.println(v4.binarySearch(arr, 5));
        HashMap<Integer,Integer>map=new LinkedHashMap<>();
    }
}
