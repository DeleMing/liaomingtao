package org.lmt.consumer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Properties;
import java.util.UUID;

/**
 * @author: LiaoMingtao
 * @date: 2022/8/24
 */
public class Consumer11 {

    private final KafkaConsumer<String, String> consumer;

    private static final String OUT_PUT_FILE_PATH = "F:\\Users\\76149\\Downloads\\zhibiao.log";

    public Consumer11() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.70.109:9092,192.168.70.110:9092,192.168.70.111:9092");
        props.put("zookeeper.connect", "192.168.70.109:2181,192.168.70.110:2181,192.168.70.111:2181");
        props.put("group.id", UUID.randomUUID().toString());
        props.put("enable.auto.commit", "false");
        props.put("zookeeper.session.timeout.ms", "10000");
        props.put("zookeeper.sync.time.ms", "200");
        props.put("auto.commit.interval.ms", "1000");
        // earliest latest
        props.put("auto.offset.reset", "earliest");
        // 序列化 props.put("serializer.class", "kafka.serializer.StringEncoder");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        // props.put("value.deserializer", "org.apache.kafka.common.serialization.ByteArrayDeserializer");

        consumer = new KafkaConsumer<>(props);
    }

    public void consume() {
        consumer.subscribe(Collections.singletonList("ods_all_metric"));
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                String value = record.value();
                try {
                    FileWriter fileWriter = new FileWriter(OUT_PUT_FILE_PATH, true);
                    BufferedWriter bw = new BufferedWriter(fileWriter);
                    bw.write(value + "\n");
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        new Consumer11().consume();
    }
}
