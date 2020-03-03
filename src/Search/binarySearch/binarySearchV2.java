package Search.binarySearch;

public class binarySearchV2 {
    /*
     数组是有序的但是存在重复元素
     目标：找出最后一个等于目标值的下标
    */
    public int binarySearch(int[]arr,int target){
        int low=0;
        int high=arr.length-1;
        while (low<high){
            int mid=(low+high)/2;
            if(arr[mid]==target){
               /*
                    最后等于目标值的有两种情况：
               1. 在mid=arr.length-1的位置 2. arr[mid]=target && arr[mid+1]!=target
                */
                if(mid==arr.length-1)
                    return mid;
                if(arr[mid+1]!=target)
                    return mid;
                low=mid;
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
        int arr[]={1,5,5,5,9};
        binarySearchV2 v2=new binarySearchV2();
        System.out.println(v2.binarySearch(arr, 5));
    }
}
