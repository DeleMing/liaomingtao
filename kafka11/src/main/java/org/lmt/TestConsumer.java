package org.lmt;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.lmt.consumer.ConstantConsumer;

import java.util.Collections;
import java.util.Properties;

/**
 * @author: LiaoMingtao
 * @date: 2022/8/24
 */
public class TestConsumer extends Thread {

    /**
     * 定义kafka消费者
     */
    private static KafkaConsumer<String, String> consumer;

    /**
     * 定义主题
     */
    private static final String TOPIC = "lmt";

    /**
     * 初始化kafka消费者
     */
    public TestConsumer() {
        Properties properties = new Properties();
        properties.put(ConstantConsumer.BOOTSTRAP_SERVERS, "192.168.70.109:9092");
        // ConstantConsumer.GROUP_ID 这个参数的value需要和服务器kafka/config/consumer.properties文件中的group.id=test-consumer-group Value一致,
        // 否则会造成消费者无法消费的情况
        properties.put(ConstantConsumer.GROUP_ID, "test-consumer-group");
        properties.put(ConstantConsumer.ENABLE_AUTO_COMMIT, "true");
        properties.put(ConstantConsumer.AUTO_COMMIT_INTERVAL_MS, 1000);
        properties.put(ConstantConsumer.SESSION_TIMEOUT_MS, 30000);
        // earliest latest
        properties.put(ConstantConsumer.AUTO_OFFSET_RESET, "earliest");
        properties.put(ConstantConsumer.KEY_DESERIALIZER, "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put(ConstantConsumer.VALUE_DESERIALIZER, "org.apache.kafka.common.serialization.StringDeserializer");
        consumer = new KafkaConsumer<>(properties);
    }

    /**
     * 消费消息
     */
    public void getConsumers() {
        consumer.subscribe(Collections.singletonList(TOPIC));
        while (true) {
            //TODO 消费数据，stdout
            ConsumerRecords<String, String> records = consumer.poll(1000);
            for (ConsumerRecord<String, String> consumerRecords : records) {
                System.out.println("key:" + consumerRecords.key() + ", value: " + consumerRecords.value() + ", topic: " + consumerRecords.topic());
            }
        }
    }


    public static void main(String[] args) {
        new TestConsumer().getConsumers();
    }
}
