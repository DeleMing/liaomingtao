package org.lmt;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.lmt.consumer.ConstantConsumer;
import org.lmt.consumer.Consumer;
import org.lmt.consumer.ConsumerPool;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: LiaoMingtao
 * @date: 2022/8/24
 */
public class TestConsumer {

    public static void main(String[] args) {
        Map<String, Object> map = new HashMap<>(8, 1);
        map.put(ConstantConsumer.BOOTSTRAP_SERVERS, "192.168.70.109:9092");
        // ConstantConsumer.GROUP_ID 这个参数的value需要和服务器kafka/config/consumer.properties文件中的group.id=test-consumer-group Value一致,
        // 否则会造成消费者无法消费的情况
        map.put(ConstantConsumer.GROUP_ID, "test-consumer-group3");
        map.put(ConstantConsumer.ENABLE_AUTO_COMMIT, "true");
        map.put(ConstantConsumer.AUTO_COMMIT_INTERVAL_MS, 1000);
        map.put(ConstantConsumer.SESSION_TIMEOUT_MS, 30000);
        // earliest latest
        map.put(ConstantConsumer.AUTO_OFFSET_RESET, "earliest");
        Consumer consumer = ConsumerPool.getInstance(map).getConsumer();

        consumer.StrSubscribe(Collections.singletonList("lmt"));
        while (true) {
            //TODO 消费数据，stdout
            ConsumerRecords<String, String> records = consumer.StrSubscribe(1000L);
            for (ConsumerRecord<String, String> consumerRecords : records) {
                System.out.println("key:" + consumerRecords.key() + ", value: " + consumerRecords.value() + ", topic: " + consumerRecords.topic());
            }
        }
    }
}
