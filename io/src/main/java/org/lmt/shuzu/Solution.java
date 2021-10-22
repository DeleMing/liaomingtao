package org.lmt.shuzu;

/**
 * @author: LiaoMingtao
 * @date: 2021/9/17
 */
public class Solution {

    public static void main(String[] args) {
        int[] a = {1, 2, 3, 1, 2, 3, 2, 1,4,5};
        System.out.println(majorityElement(a));
    }

    public static int majorityElement(int[] nums) {
        int cnt = 0, major = 0;
        for (int num : nums) {
            if (cnt == 0) {
                major = num;
                cnt = 1;
            } else {
                cnt += (major == num ? 1 : -1);
            }
            System.out.println(cnt + ":" + major);
        }
        return major;
    }
}
