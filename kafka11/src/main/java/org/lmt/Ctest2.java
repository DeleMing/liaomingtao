package org.lmt;

import org.lmt.producer.Producer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: LiaoMingtao
 * @date: 2021/4/6
 */
public class Ctest2 {

    public static void main(String[] args) throws InterruptedException {
        // long size = 10000000L * 10;
        String topicName = "ods_all_metric";
        long size = 10000;

        Map<String, Object> configMap = new HashMap<>(8);

        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        for (int i = 0; i <size; i++) {
            Thread.sleep(1000);
            String format = sf.format(new Date());
            String json = String.format("ccccccc");
            Producer producer = Producer.getInstance(configMap);
            producer.sendStr(topicName, json);
        }

    }
}
