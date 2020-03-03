package Sort;

public class quickSort {
    public void quickSort(int arr[],int left,int right){
        if(left<right){
            int pivotIndex=partition(arr, left, right);
            quickSort(arr,left,pivotIndex-1);
            quickSort(arr, pivotIndex+1, right);
        }
    }

    //基于基准元素的排列，基准元素选择待排序序列的第一个元素
    public int partition(int arr[],int left,int right){
        int pivot=left;
        int smallerOnright=-1;
        int biggerOnLeft=-1;
        boolean sFlag=true;
        boolean bFlag=true;
        while (left<right){
            if(bFlag==true&&arr[right]>arr[pivot]){
                right--;
                sFlag=false;
            }else {
                smallerOnright=right;
                sFlag=true;
            }

            if(sFlag==true&&arr[left]<=arr[pivot]){
                left++;
                bFlag=false;
            }else{
                biggerOnLeft=left;
                bFlag=true;
            }
            if(bFlag==true&&sFlag==true){
                swap(arr, smallerOnright, biggerOnLeft);
            }
        }
        swap(arr, pivot, right);
        return right;
    }
    //交换两个元素
    private void swap(int[]arr,int aIndex,int bIndex){
        int temp=arr[aIndex];
        arr[aIndex]=arr[bIndex];
        arr[bIndex]=temp;
    }

    public static void main(String[] args) {
        int[]arr={8,7,6,3,4,5};
        quickSort qs=new quickSort();
        qs.quickSort(arr, 0, arr.length-1);
        for(int i=0;i<arr.length;i++){
            System.out.println(arr[i]);
        }
    }

}


