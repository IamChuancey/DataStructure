package Sort;

public class mergeSort {
   public void mergeSort(int[]arr,int start,int end){
       if(start<end){
          int mid=(start+end)/2; //数组一分为二
          mergeSort(arr, start, mid); //递归地将左部分一分为二
          mergeSort(arr, mid+1, end);//递归地将右部分一分为二
          merge(arr, start,mid,end); //合并两个有序数组
       }
   }

   private void merge(int[]arr,int start,int mid,int end){
      int smaller=start;
      int bigger=mid+1;
      int res[]=new int[end-start+1];
      int i=0;
      while (smaller<=mid&&bigger<=end){
         if(arr[smaller]<=arr[bigger]){
            res[i++]=arr[smaller++];
         }else if(arr[smaller]>arr[bigger]){
            res[i++]=arr[bigger++];
         }
      }
      while (smaller<=mid){
         res[i++]=arr[smaller++];
      }
      while (bigger<=end){
         res[i++]=arr[bigger++];
      }
      //将res数组的元素赋值给arr
      for(int j=0;j<res.length;j++){
         arr[start+j]=res[j];
      }
   }

   //合并两个有序数组
   public int[] mergeTwoOrderedArray(int[]arr1,int[]arr2){
      int smaller=0;
      int bigger=0;
      int res[]=new int[arr1.length+arr2.length];
      int i=0;
      while (smaller<arr1.length&&bigger<arr2.length){
         if(arr1[smaller]<=arr2[bigger]){
            res[i]=arr1[smaller];
            smaller++;
         }
         else if(arr1[smaller]>arr2[bigger]){
            res[i]=arr2[bigger];
            bigger++;
         }
         i++;
      }
      while (smaller<arr1.length){
         res[i++]=arr1[smaller++];
      }
      while (bigger<arr2.length){
         res[i++]=arr2[bigger++];
      }
      return res;
   }

   public static void main(String[] args) {
      int[]arr={5,1,3,2,7,1};
      mergeSort ms=new mergeSort();
      ms.mergeSort(arr, 0,arr.length-1);
      for(int i=0;i<arr.length;i++){
         System.out.println(arr[i]);
      }
   }
}
