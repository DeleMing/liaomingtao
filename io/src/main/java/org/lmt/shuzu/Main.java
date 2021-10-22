package org.lmt.shuzu;

import com.alibaba.fastjson.JSON;

/**
 * @author: LiaoMingtao
 * @date: 2021/9/17
 */
public class Main {
    public static void main(String[] args) {
        int[] a = {0, 0};
        int i = removeDuplicates(a);
        System.out.println(i);
    }


    public static int removeDuplicates(int[] nums) {
        int cnt = 0, n = nums.length;
        for (int i = 1; i < n; ++i) {

            if (nums[i] == nums[i - 1]) {
                ++cnt;
            } else {
                nums[i - cnt] = nums[i];
            }
            System.out.println(i + ":" + JSON.toJSONString(nums));
        }
        for (int num : nums) {
            System.out.println(num);
        }
        return n - cnt;
    }

    public static int removeDuplicates2(int[] nums) {
        int cnt = 0, cur = 1;
        for (int i = 1; i < nums.length; ++i) {
            if (nums[i] == nums[i - 1]) {
                ++cnt;
            } else {
                cnt = 0;
            }
            if (cnt < 2) {
                nums[cur++] = nums[i];
            }
        }
        return cur;
    }
}
