package org.lmt;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: LiaoMingtao
 * @date: 2021/9/16
 */
public class Ctest7 {

    public static void main(String[] args) {
        int maxCount = 2;
        int count = 0;
        List<String> resultList = new ArrayList<>();
        List<String> tempList = new ArrayList<>();
        List<String> in = new ArrayList<>();
        in.add("c");
        in.add("b");
        in.add("aaa");
        in.add("b");
        in.add("c");
        in.add("d");
        in.add("e");
        in.add("f");
        in.add("aaa");
        in.add("d");
        in.add("e");
        in.add("aaa");
        in.add("f");
        in.add("d");
        in.add("f");
        in.add("a");
        for (String kcbpLog : in) {
            if (count == 0 && kcbpLog.contains("a")) {
                // 首条请求
                tempList.add(kcbpLog);
                count++;
                continue;
            }
            if (!kcbpLog.contains("a") && count !=0) {
                tempList.add(kcbpLog);
                count++;
                continue;
            }
            if (count > maxCount) {
                resultList.add(JSON.toJSONString(tempList));
                tempList.clear();
                count=0;
            }
            if (kcbpLog.contains("a")) {
                // 新的一条
                resultList.add(JSON.toJSONString(tempList));
                tempList.clear();
                tempList.add(kcbpLog);
                count = 1;
            }
        }

        if (!tempList.isEmpty()) {
            resultList.add(JSON.toJSONString(tempList));
            tempList.clear();
        }

        for (String s : resultList) {
            System.out.println(s);
        }
    }
}
