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
        String topicName = "metric1";
        long size = 100000;

        Map<String, Object> configMap = new HashMap<>(8);

        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        while (true) {
            Thread.sleep(1000);
            String format = sf.format(new Date());
            String json = String.format("{ \"type\": \"execbeat\", \"collecttime\": \"2021-04-14T11:02:00.000Z\", \"transip\": \"10.180.204.83\", \"collector_type\": \"execbeat\", \"@timestamp\": \"2021-04-14T11:02:00.000Z\", \"appsystem\": \"znxgSystem\", \"host\": \"xg_exec3\", \"transtime\": \"2021-04-14T11:01:53.106Z\", \"ip\": \"10.180.212.112\", \"@version\": \"1\", \"appprogramname\": \"xg\", \"clustername\": \"监控集群\", \"servicename\": \"xg\", \"exec\": { \"command\": \"python\", \"stdout\": \"{\\\"metricsetname\\\": \\\"check_session_win\\\", \\\"timestamp\\\": \\\"%s\\\", \\\"measures\\\": {\\\"check_session\\\": 0}, \\\"dimensions\\\": {\\\"ip\\\": \\\"10.180.212.112\\\", \\\"hostname\\\": \\\"xg_exec3\\\", \\\"result\\\": 2, \\\"appsystem\\\": \\\"znxgSystem\\\"}}\\n\", \"exitCode\": 0 }, \"topicname\": \"ods_all_metric\", \"beat\": { \"name\": \"xg_exec3\", \"hostname\": \"xg_exec3\", \"version\": \"3.2.0\" }, \"collectruleid\": 1488, \"tags\": [ \"beats_input_raw_event\" ], \"servicecode\": \"xg\" }",format);
            Producer producer = Producer.getInstance(configMap);
            producer.sendStr(topicName, json);
        }

    }
}
