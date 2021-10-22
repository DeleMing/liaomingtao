package org.lmt;

import lombok.Data;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * @author: LiaoMingtao
 * @date: 2021/8/31
 */
@Data
public class WordCount implements Callable<Map<String, Integer>> {

    private String str;

    public WordCount(String str) {
        this.str = str;
    }

    @Override
    public Map<String, Integer> call() throws Exception {
        str = str.toLowerCase(Locale.ROOT);
        String[] strArr = str.split("[ ,]+|[ .]+|[\\r\\n]+");
        // String[] strArr = str.split("\\.|\\,|\\   |\\ ");
        Map<String, Integer> map = new HashMap<>(8);
        for (String i : strArr) {
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
}
