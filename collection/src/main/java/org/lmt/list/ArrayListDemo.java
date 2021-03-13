package org.lmt.list;

import java.util.ArrayList;
import java.util.List;

/**
 * 1、有序、可重复
 * 2、底层使用数组
 * 3、线程不安全
 * 4、容量不够时，ArrayList时当前容量*1.5+1,默认容量为10
 * 5、速度快、增删慢（适合随机查找和遍历，不适合插入和删除）
 * @author: LiaoMingtao
 * @date: 2021/2/27
 */
public class ArrayListDemo {

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        List<Integer> demoList = new ArrayList<>();
        // int size = 10000;
        // for (int i= 0;i <  size;i++) {
        //     demoList.add()
        // }
        // demoList.add("a");
        // demoList.add("b");
        // demoList.add("c");
        long endTime = System.currentTimeMillis();
    }
}
