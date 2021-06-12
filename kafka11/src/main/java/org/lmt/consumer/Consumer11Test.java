package org.lmt.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.lmt.avro.AvroDeserializerFactory;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * @author: LiaoMingtao
 * @date: 2021/3/13
 */
@Slf4j
public class Consumer11Test {

    private final KafkaConsumer<String, byte[]> consumer;

    private static final String OUT_PUT_FILE_PATH = "F:\\Users\\Thinkpad\\Downloads\\test.log";

    public Consumer11Test() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.30.42:9092");
        props.put("zookeeper.connect", "192.168.30.42:2181");
        props.put("group.id", UUID.randomUUID().toString());
        props.put("enable.auto.commit", "false");
        props.put("zookeeper.session.timeout.ms", "10000");
        props.put("zookeeper.sync.time.ms", "200");
        props.put("auto.commit.interval.ms", "1000");
        // earliest latest
        props.put("auto.offset.reset", "latest");
        // 序列化 props.put("serializer.class", "kafka.serializer.StringEncoder");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        // props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.ByteArrayDeserializer");

        consumer = new KafkaConsumer<>(props);
    }

    public void consume() {
        consumer.subscribe(Arrays.asList("dwd_all_metric"));
        int count = 0;
        while (true) {
            ConsumerRecords<String, byte[]> records = consumer.poll(100);
            for (ConsumerRecord<String, byte[]> record : records){
                GenericRecord deserialize = AvroDeserializerFactory.getMetricDeserializer()
                        .deserialize(record.value());
                // if (deserialize.toString().contains("check_session_win")) {
                    try {
                        FileWriter fileWriter = new FileWriter(OUT_PUT_FILE_PATH, true);
                        BufferedWriter bw = new BufferedWriter(fileWriter);
                        bw.write(deserialize.toString() + "\n");
                        bw.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                // }


            }
            log.info("总数：{}", count);
        }
    }

    public static void main(String[] args) {
        new Consumer11Test().consume();
    }
}
