package org.lmt.paixu;


/**
 * 相邻两条数据排序，讲最小或最大的数据放入尾部，然后一次循环，每次循环的长度都会减1
 *
 * @author: LiaoMingtao
 * @date: 2021/9/16
 */
public class Maopao {

    public static void main(String[] args) {
        int[] a = {1, 2, 7, 9, 5, 8};
        long startTime = System.currentTimeMillis();
        bubbleSort1(a);
        long endTime = System.currentTimeMillis();
        System.out.println("当前程序耗时：" + (endTime - startTime) + "ms");
        for (int i : a) {
            System.out.println(i);
        }
    }


    private static void bubbleSort2(int[] nums) {
        // 设定一个标记，若为false，则表示此次循环没有进行交换，也就是待排序列已经有序，排序已经完成。
        boolean hasChange = true;
        for (int i = 0, n = nums.length; i < n - 1 && hasChange; ++i) {
            hasChange = false;
            for (int j = 0; j < n - i - 1; ++j) {
                if (nums[j] < nums[j + 1]) {
                    swap(nums, j, j + 1);
                    hasChange = true;
                }
            }
        }
    }

    /**
     * 缺陷:没有考虑排序后，是否未排序的已经有序了
     *
     * @param a
     */
    public static void bubbleSort1(int[] a) {
        int length = a.length;
        // 循环长度
        for (int i = 0; i < length - 1; i++) {
            int j = length - i - 1;
            for (int k = 0; k < j; k++) {
                if (a[k] < a[k + 1]) {
                    swap(a, k, k + 1);
                }
            }
        }
    }

    public static void bubbleSort(int[] a) {
        for (int k = 0; k < a.length; k++) {
            for (int i = 0; i < a.length; i++) {
                int j = 0;
                if (i == a.length - 1) {
                    j = i;
                } else {
                    j = i + 1;
                }
                if (a[i] < a[j]) {
                    swap(a, i, j);
                }
            }
        }
    }

    public static void swap(int[] a, int i, int j) {
        int b = a[i];
        a[i] = a[j];
        a[j] = b;
    }
}
