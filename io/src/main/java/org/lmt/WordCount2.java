package org.lmt;

import lombok.Data;

import java.util.Locale;

/**
 * @author: LiaoMingtao
 * @date: 2021/8/31
 */
@Data
public class WordCount2 implements Runnable {

    private String str;

    public WordCount2(String str) {
        this.str = str;
    }

    @Override
    public void run() {
        str = str.toLowerCase(Locale.ROOT);
        String[] strArr = str.split("[ ,]+|[ .]+|[\\r\\n]+");
        // String[] strArr = str.split("\\.|\\,|\\   |\\ ");
        for (String i : strArr) {
            ThreadReadLargeFiles2.operateMap(i);
        }
    }
}
