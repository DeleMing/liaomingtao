package org.lmt;

import com.alibaba.fastjson.JSONObject;
import org.lmt.producer.Producer;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static java.lang.System.currentTimeMillis;

/**
 * @author: LiaoMingtao
 * @date: 2021/4/6
 */
public class Ctest1 {

    public static String buildMsg() {
        JSONObject filebeatJson = new JSONObject();
        JSONObject metadataJson = new JSONObject();
        metadataJson.put("beat", "filebeat");
        metadataJson.put("type", "doc");
        metadataJson.put("version", "6.8.1");
        JSONObject inputJson = new JSONObject();
        inputJson.put("type", "log");
        JSONObject beatJson = new JSONObject();
        beatJson.put("name", "zorkdata-151");
        beatJson.put("hostname", "zorkdata-151");
        beatJson.put("version", "6.8.1");
        JSONObject hostJson = new JSONObject();
        hostJson.put("name", "zorkdata-151");
        hostJson.put("architecture", "x86_64");
        hostJson.put("id", "8e3dfc85999b4e02bae4adf4b92b909a");
        hostJson.put("containerized", "false");
        JSONObject logJson = new JSONObject();
        logJson.put("file", "{ \"path\": \"/var/log/nginx/access.log\" }");
        filebeatJson.put("@timestamp", Instant.now().toString());
        filebeatJson.put("source", "/var/log/nginx/access.log");
        filebeatJson.put("offset", String.valueOf(currentTimeMillis()));

        StringBuilder message = new StringBuilder();
        String strs = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for (int i = 0; i < 345; i++) {
            message.append(strs.charAt((int) (Math.random() * 26)));
        }
        filebeatJson.put("message", message);

        Random random = new Random();
        int i = random.nextInt(10);
        if (i % 2 == 0) {
            filebeatJson.put("appsystem", "dev_test");

        } else {
            filebeatJson.put("appsystem", "network");
        }
        filebeatJson.put("appprogramname", "test_appprogramname");
        filebeatJson.put("servicename", "test_servicename");
        filebeatJson.put("servicecode", "test_cdde");

        filebeatJson.put("@metadata", metadataJson);
        filebeatJson.put("input", inputJson);
        filebeatJson.put("beat", beatJson);
        filebeatJson.put("host", hostJson);
        filebeatJson.put("log", logJson);
        filebeatJson.put("prospector", inputJson);
        return filebeatJson.toJSONString();
    }

    public static void main(String[] args) throws InterruptedException {
        // long size = 10000000L * 10;
        String topicName = "log_test_nhx1";
        long size = 1000;

        Map<String, Object> configMap = new HashMap<>(8);
        for (int i = 0; i < size; i++) {
            String json = "{\"appprogramname\":\"linux模块\",\"transip\":\"192.168.70.172\",\"topicname\":\"ods_default_log\",\"servicecode\":\"linux模块\",\"collectruleid\":3,\"appsystem\":\"dev_test\",\"log\":{\"offset\":483679,\"file\":{\"path\":\"/var/log/monit.log\"}},\"@version\":\"1\",\"servicename\":\"linux模块\",\"@timestamp\":\"2021-06-10T07:26:11.278Z\",\"ecs\":{\"version\":\"1.1.0\"},\"input\":{\"type\":\"log\"},\"clustername\":\"基础监控\",\"tags\":[\"beats_input_codec_plain_applied\"],\"message\":\"[CST Jun 10 15:26:06] error    : Aborting queued event '/var/monit/1616146454_16107e0' - service ostemplate not found in monit configuration\",\"collecttime\":\"2021-06-10T07:26:11.278Z\",\"ip\":\"192.168.70.172\",\"host\":{\"name\":\"yf172\",\"hostname\":\"yf172\",\"architecture\":\"x86_64\",\"containerized\":false,\"id\":\"560a9e4ea53645668420a2c269bf8fe4\",\"os\":{\"name\":\"CentOS Linux\",\"kernel\":\"3.10.0-1062.el7.x86_64\",\"platform\":\"centos\",\"version\":\"7 (Core)\",\"family\":\"redhat\",\"codename\":\"Core\"}},\"agent\":{\"id\":\"62e87a9a-fb60-43a4-acf9-0f0cc07147bd\",\"ephemeral_id\":\"e76a4912-b7f2-46fc-9ab5-f4c54e2b51a2\",\"hostname\":\"yf172\",\"version\":\"7.4.0\",\"type\":\"filebeat\"},\"transtime\":\"2021-06-10T07:26:12.380Z\"}";
            Producer producer = Producer.getInstance(configMap);
            producer.sendStr(topicName, json);
            Thread.sleep(1000);
        }

    }


}
