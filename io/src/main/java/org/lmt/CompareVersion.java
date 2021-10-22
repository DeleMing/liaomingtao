package org.lmt;

/**
 * @author: LiaoMingtao
 * @date: 2021/9/1
 */
public class CompareVersion {

    public static void main(String[] args) {
        int i = compareVersion2("111.0.0", "11.0.0.0");
        System.out.println(i);
    }

    public static int compareVersion(String version1, String version2) {
        String[] a1 = version1.split("\\.");
        String[] a2 = version2.split("\\.");

        for(int n = 0; n < Math.max(a1.length, a2.length); n++){
            int i = (n < a1.length ? Integer.parseInt(a1[n]) : 0);
            int j = (n < a2.length ? Integer.parseInt(a2[n]) : 0);
            if(i < j) {
                return -1;
            } else if(i > j) {
                return 1;
            }
        }
        return 0;
    }

    public static int compareVersion2(String version1, String version2) {

        // 两个字符串的长度
        int n = version1.length(), m = version2.length();

        int i = 0;
        int j = 0;

        while (i < n || j < m) {
            // 用v1,v2来计算每一个块中版本号的大小
            int v1 = 0;
            int v2 = 0;

            // 若当前的字符不是分隔符，则计算
            while (i < n && version1.charAt(i) != '.') {
                v1 = v1 * 10 + version1.charAt(i) - '0';
                i++;
            }


            while (j < m && version2.charAt(j) != '.') {
                v2 = v2 * 10 + version2.charAt(j) - '0';
                j++;
            }
            // 判断当前块中的版本号是否一致
            if (v1 != v2) {
                if (v1 > v2) {
                    return 1;
                }
                return -1;
            }
            // 跳过分隔符
            i++;
            j++;
        }

        // 全部比较完了，没有不等的则返回0
        return 0;

    }
}
