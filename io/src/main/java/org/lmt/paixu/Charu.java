package org.lmt.paixu;

/**
 * 插入排序类比打扑克牌，从第二个数据开始算
 *
 * @author: LiaoMingtao
 * @date: 2021/9/16
 */
public class Charu {

    public static void main(String[] args) {
        int[] a = {1, 2, 7, 9, 5, 8};
        int j = 0;
        for (int i = 1; i < a.length; i++) {
            int num = a[i];
            for (j = i - 1; j >= 0 && a[j] < num; j--) {
                a[j + 1] = a[j];
            }
            a[j + 1] = num;
        }
        for (int i : a) {
            System.out.println(i);
        }
    }


    private static void insertionSort(int[] nums) {
        for (int i = 1, j, n = nums.length; i < n; ++i) {
            int num = nums[i];
            for (j = i - 1; j >= 0 && nums[j] > num; --j) {
                nums[j + 1] = nums[j];
            }
            nums[j + 1] = num;
        }
    }
}
