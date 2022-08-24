package org.lmt.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author: LiaoMingtao
 * @date: 2022/8/24
 */
@Slf4j
public class Consumer implements Serializable {

    private static KafkaConsumer<String, String> consumer;
    private static KafkaConsumer<String, byte[]> consumerByte;

    public Consumer(Map<String, Object> conf) {
        try {
            consumer = getStrConsumer(conf);
            consumerByte = getByteConsumer(conf);
        } catch (Exception e) {
            log.error("初始化Kafka失败,系统自动退出!", e);
            throw new RuntimeException("初始化Kafka失败,系统自动退出!");
        }
    }

    public void StrSubscribe(List<String> topics) {
        consumer.subscribe(topics);
    }

    public void byteSubscribe(List<String> topics) {
        consumerByte.subscribe(topics);
    }

    public ConsumerRecords<String, String> StrSubscribe(Long timeout) {
        return consumer.poll(timeout);
    }

    public ConsumerRecords<String, byte[]> byteSubscribe(Long timeout) {
        return consumerByte.poll(timeout);
    }

    public KafkaConsumer<String, String> getStrConsumer(Map<String, Object> conf) {
        Properties properties = getProperties(conf);
        return new KafkaConsumer<>(properties);
    }

    public KafkaConsumer<String, byte[]> getByteConsumer(Map<String, Object> conf) {
        Properties properties = getProperties(conf);
        properties.put(ConstantConsumer.VALUE_DESERIALIZER, "org.apache.kafka.common.serialization.ByteArrayDeserializer");
        return new KafkaConsumer<>(properties);
    }

    public Properties getProperties(Map<String, Object> conf) {
        Properties properties = new Properties();
        for (Map.Entry<String, Object> next : conf.entrySet()) {
            properties.put(next.getKey(), next.getValue());
        }
        properties.put(ConstantConsumer.KEY_DESERIALIZER, "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put(ConstantConsumer.VALUE_DESERIALIZER, "org.apache.kafka.common.serialization.StringDeserializer");
        return properties;
    }
}
