package Search.binarySearch;

public class binarySearchV3 {
    /*
    数组是有序的但是存在重复元素
    目标：找出第一个大于等于给定值的元素
   */
    public int binarySearch(int[]arr,int target){
        int low=0;
        int high=arr.length-1;
        while (low<high){
            int mid=(low+high)/2;
            if(arr[mid]<target){
                low=mid;
            }
            if(arr[mid]>=target){
                if(mid==0||arr[mid-1]<target)
                return mid;
                high=mid;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int arr[]={1,5,5,7,9};
        binarySearchV3 v3=new binarySearchV3();
        System.out.println(v3.binarySearch(arr, 1));
    }
}
