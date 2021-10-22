package org.lmt;

/**
 * @author: LiaoMingtao
 * @date: 2021/9/16
 */
public class Ctest6 {

    public static void main(String[] args) {
        String str = "runoob";
        str.toUpperCase();
        str += "wwwrunoobcom";
        String string = str.substring(2,13);
        System.out.println(str);
        System.out.println(str.charAt(0));
        string = string + str.charAt(4);;

        System.out.println(string);

    }
}
