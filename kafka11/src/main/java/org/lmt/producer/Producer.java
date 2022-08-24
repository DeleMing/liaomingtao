package org.lmt.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.Serializable;
import java.util.Map;


/**
 * @author Thinkpad
 */
@Slf4j
public class Producer implements Serializable {
    private static final long serialVersionUID = -1L;

    private final KafkaProducer<String, byte[]> producer;
    private final KafkaProducer<String, String> noAvroProducer;


    public Producer(Map<String, Object> conf) {
        try {
            producer = KafkaTools.getAvroProducer(conf);
            noAvroProducer = KafkaTools.getStrProducer(conf);
        } catch (Exception e) {
            log.error("初始化Kafka失败,系统自动退出!", e);
            throw new RuntimeException("初始化Kafka失败,系统自动退出!");
        }

    }

    /**
     * 发送avro字节数组数据
     *
     * @param topic    topic名称
     * @param avroByte avro字节数组
     */
    public void sendAvro(String topic, byte[] avroByte) {
        try {
            producer.send(new ProducerRecord<>(topic, null, avroByte));
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }

    /**
     * 发送字符串数据
     *
     * @param topic topic名称
     * @param str   字符串数据
     */
    public void sendStr(String topic, String str) {
        try {
            noAvroProducer.send(new ProducerRecord<>(topic, null, str));
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }


}
