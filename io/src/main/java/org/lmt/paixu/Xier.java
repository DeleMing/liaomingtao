package org.lmt.paixu;

/**
 * @author: LiaoMingtao
 * @date: 2021/9/17
 */
public class Xier {

    public static void main(String[] args) {
        int[] arr = {1, 2, 7, 9, 5, 8, 4};


    }


    /**
     * 核心代码---开始
     *
     * @param arr
     */
    public static void sort(Comparable[] arr) {
        int j;
        for (int gap = arr.length / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < arr.length; i++) {
                Comparable tmp = arr[i];
                for (j = i; j >= gap && tmp.compareTo(arr[j - gap]) < 0; j -= gap) {
                    arr[j] = arr[j - gap];
                }
                arr[j] = tmp;
            }
        }
    }

}
