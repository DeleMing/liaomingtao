package org.lmt;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * @author: LiaoMingtao
 * @date: 2021/8/30
 */
public class Ctest3 {

    public static void main(String[] args) {
        Map<String, Integer> map = new HashMap<>();
        String line = "Our current preoccupation with zombies and vampires is easy to explain. They're two sides of the same coin, addressing our fascination with sex, death and food.   ";
        line += "Our current preoccupation with zombies and vampires is easy to explain. They're two sides of the same coin, addressing our fascination with sex, death and food.   ";

        spiltAndCount(line, map);
        //这里将map.entrySet()转换成list
        List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(map.entrySet());
        //然后通过比较器来实现排序
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            //升序排序
            @Override
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }

        });
        for (Map.Entry<String, Integer> mapping : list) {
            System.out.println(mapping.getKey() + ":" + mapping.getValue());
        }
        System.out.println(JSON.toJSONString(map));
    }

    public static void spiltAndCount(String line, Map<String, Integer> map) {
        String regex = "\\.|\\,|\\   |\\ ";/*/[\s]|[ ]|[,]|[.]|[“”]|[?]|[  ]*/
        // String regex = "";/*/[\s]|[ ]|[,]|[.]|[“”]|[?]|[  ]*/
        String[] words = line.split(regex);
        for (int i = 0; i < words.length; i++) {
            if (StringUtils.isEmpty(words[i])) {
                continue;
            }
            if (map.containsKey(words[i])) {
                map.put(words[i], map.get(words[i]) + 1);
            } else {
                map.put(words[i], 1);
            }
        }
    }
}
