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
import java.util.Arrays;
import java.util.Properties;
import java.util.UUID;

/**
 * @author: LiaoMingtao
 * @date: 2021/3/24
 */
@Slf4j
public class Consumer11StringTest {

    private final KafkaConsumer<String, String> consumer;

    private static final String OUT_PUT_FILE_PATH = "F:\\Users\\Thinkpad\\Downloads\\yuanshi.log";

    public Consumer11StringTest() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.30.42:9092,192.168.30.43:9092,192.168.30.44:9092");
        props.put("zookeeper.connect", "192.168.30.42:2181");
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
        consumer.subscribe(Arrays.asList("ods_all_metric"));
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records){
                // if (record.value().contains("check_session_win")) {
                    try {
                        FileWriter fileWriter = new FileWriter(OUT_PUT_FILE_PATH, true);
                        BufferedWriter bw = new BufferedWriter(fileWriter);
                        bw.write(record.value() + "\n");
                        bw.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                // }
            }
        }
    }

    public static void main(String[] args) {
        new Consumer11StringTest().consume();
    }
}
