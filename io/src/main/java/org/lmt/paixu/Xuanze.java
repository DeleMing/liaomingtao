package org.lmt.paixu;

/**
 * @author: LiaoMingtao
 * @date: 2021/9/17
 */
public class Xuanze {

    public static void main(String[] args) {
        int[] a = {1, 2, 7, 9, 5, 8};

        selectSort(a);
        for (int i : a) {
            System.out.println(i);
        }
    }

    public static void selectSort(int[] a) {
        int i;        // 有序区的末尾位置

        int j;        // 无序区的起始位置

        int min;    // 无序区中最小元素位置

        for (i = 0; i < a.length; i++) {
            min = i;

            //找"a[i+1]..a[n]"之间最小元素，并赋给min
            for (j = i + 1; j < a.length; j++) {
                if (a[j] < a[min]) {
                    min = j;
                }
            }

            //若min!=i，则交换 a[i] 和 a[min]。
            //交换后，保证了a[0]..a[i]之间元素有序。
            if (min != i) {
                swap(a, i, min);
            }
        }
    }

    public static void swap(int[] a, int i, int j) {
        int b = a[i];
        a[i] = a[j];
        a[j] = b;
    }
}
