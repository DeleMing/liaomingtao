package org.lmt;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author：hubinbin
 * @date：2021/8/30
 */
public class WordThread implements Callable {
    private String lineString;

    public WordThread(String lineString) {
        this.lineString = lineString;
    }

    @Override
    public Map<String, Integer> call() throws Exception {
        String[] lineArray = lineString.split("[ ,]+|[ .]+");
        Map<String, Integer> map = new HashMap<>(1024);
        for (String i : lineArray) {
            Integer count = map.get(i);
            if (null == count) {
                map.put(i, 1);
                continue;
            }
            count = count + 1;
            map.put(i, count);
        }
        map.remove("\n");
        return map;
    }

    //    public static void main(String[] args) {
//        String test = "It is a,special day today. bayberries. ";
//        String[] testArray = test.split("[ ,]+|[ .]+");
//        System.out.println(testArray);
//    }
}
