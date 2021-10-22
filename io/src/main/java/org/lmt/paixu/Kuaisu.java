package org.lmt.paixu;

/**
 * @author: LiaoMingtao
 * @date: 2021/9/17
 */
public class Kuaisu {

    public static void main(String[] args) {
        int[] arr = {1, 2, 7, 9, 5, 8, 4};
        QuickSort(arr, 0, arr.length-1);

        for (int i : arr) {
            System.out.println(i);
        }
    }

    public static void QuickSort(int r[], int lo, int hi){
        int i = lo, j = hi;
        int temp;
        if(i < j){
            temp = r[i];
            while (i != j)
            {
                while(j > i && r[j] > temp) {
                    -- j;
                }
                r[i] = r[j];
                while(i < j && r[i] < temp) {
                    ++ i;
                }
                r[j] = r[i];
            }
            r[i] = temp;
            QuickSort(r, lo, i - 1);
            QuickSort(r, i + 1, hi);
        }
    }
}
