package Search.binarySearch;

public class binarySearchV1 {
   /*
      数组是有序的但是存在重复元素
      目标：找出第一个等于目标值的下标
   */
   public int binarySearch(int[]arr,int target){
       int low=0;
       int high=arr.length-1;
       while (low<high){
           int mid=(low+high)/2;
           if(arr[mid]==target){
               /*
                    第一个等于目标值的有两种情况：
               1. 在index=0的位置 2. arr[mid]=target && arr[mid-1]!=target
                */
               if(mid==0)
               return mid;
               if(arr[mid-1]!=target)
               return mid;
               high=mid;
           }
           if(arr[mid]<target){
               low=mid;
           }
           if(arr[mid]>target){
               high=mid;
           }
       }
       return -1;
   }

    public static void main(String[] args) {
        int arr[]={1,5,5,7,9};
        binarySearchV1 v1=new binarySearchV1();
        System.out.println(v1.binarySearch(arr, 5));
    }
}
