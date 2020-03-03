package Sort;

public class heapSort {
    public void heapSort(int[] array) {
        //初始化堆
        initHeap(array);
        int count = array.length;
        while (count > 1) {
            int temp = array[count - 1];
            array[count - 1] = array[0];
            array[0] = temp;

            count--;//未排序的部分又少一个
            adjustHeap(array, count, 0);
        }
    }

    public void initHeap(int[] array) {
        //从最后一个非叶子节点开始建堆
        for (int root = 0; root <=array.length / 2 - 1; root++) {
           adjustHeap(array, array.length, root);
        }
    }

    public void adjustHeap(int[] array, int count, int root) {
        int maxChildIndex;
        while (root <= count / 2 - 1) {
            if (root == count / 2 - 1 && count % 2 == 0) {
                //节点数量为偶数时，最后一个非叶子节点只有左孩子
                maxChildIndex = 2 * root + 1;
            } else {
                int leftChildIndex = 2 * root + 1;
                int rightChildIndex = 2 * root + 2;

                maxChildIndex = array[leftChildIndex] > array[rightChildIndex] ?
                        leftChildIndex : rightChildIndex;
            }
            if (array[root] < array[maxChildIndex]) {
                int tmp = array[root];
                array[root] = array[maxChildIndex];
                array[maxChildIndex] = tmp;

                //这里很重要，继续调整因交换结构改变的子堆
                root = maxChildIndex;
            } else {
                return;
            }

        }
    }

    public static void main(String[] args) {
        int[] array = new int[]{12, 5, 9 , 36};
        heapSort sort=new heapSort();
        sort.initHeap(array); //这个应该也是堆排序的一部分，此处只是为了显示下结果
        System.out.println("建堆之后：");
        for(int i=0;i<array.length;i++){
            System.out.println(array[i]);
        }
        sort.heapSort(array);
        System.out.println("排序之后：");
        for(int i=0;i<array.length;i++){
            System.out.println(array[i]);
        }
    }
}
