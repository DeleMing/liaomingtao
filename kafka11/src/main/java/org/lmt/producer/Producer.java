package org.lmt.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.Serializable;
import java.util.Map;


/**
 * @author Thinkpad
 */
public class Producer implements Serializable {
    private static final long serialVersionUID = -1L;

    private Map conf;
    private KafkaProducer<String, byte[]> producer;
    private KafkaProducer<String, String> producerStr;


    static Producer testProducer;

    public static synchronized Producer getInstance(Map conf) {
        if (testProducer == null) {
            testProducer = new Producer(conf);
        }
        return testProducer;
    }


    public Producer(Map conf) {
        this.conf = conf;
        try {
            producerStr = KafkaTools.getStrProducer(conf);
        } catch (Exception ex) {
           System.err.println("初始化Kafka失败,系统自动退出!");
           ex.printStackTrace();
            throw new RuntimeException("初始化Kafka失败,系统自动退出!");
        }
    }

  

    public void sendLogAvro(String topic, byte[] logAvroByte) {
        try {
            producer.send(new ProducerRecord<>(topic, null, logAvroByte));
        } catch (Exception e) {
           System.err.println(e.toString());
        }
    }

    public void sendStr(String topic, String str) {
        try {
            producerStr.send(new ProducerRecord<>(topic, null, str));
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }



}
