package org.lmt.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;

import java.io.Serializable;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Thinkpad
 */
public class KafkaTools implements Serializable {

    private static final long serialVersionUID = -1L;

    private static String kafkaSecurityModel;

    private static AtomicInteger i = new AtomicInteger(0);

    @SuppressWarnings("all")
    public static KafkaProducer<String, String> getStrProducer(Map conf) {
        Properties props = null;
        try {
            props = getKafkaProperties(conf);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        return new KafkaProducer<String, String>(props);
    }

    @SuppressWarnings("all")
    public static KafkaProducer<String, byte[]> getAvroProducer(Map conf) {
        Properties props = null;
        try {
            props = getKafkaProperties(conf);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        KafkaProducer<String, byte[]> producer = new KafkaProducer<String, byte[]>(props);
        return producer;
    }

    /**
     * 获取 kafka Properties
     *
     * @param conf
     * @return Properties
     */
    @SuppressWarnings("all")
    private static Properties getKafkaProperties(Map conf) throws ClassNotFoundException {
        //conf = paramCheck(conf);
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "yf225:9092,yf223:9092,yf21:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, Class.forName("org.apache.kafka.common.serialization.StringSerializer"));
        // props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, Class.forName("org.apache.kafka.common.serialization.ByteArraySerializer"));
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, Class.forName("org.apache.kafka.common.serialization.StringSerializer"));
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 100);
        props.put(ProducerConfig.CLIENT_ID_CONFIG, Thread.currentThread().getName() + i.getAndIncrement());
        return props;
    }

    /**
     * kafka producer 参数校验
     *
     * @param conf
     * @return Map
     */
    @SuppressWarnings("all")
    private static Map paramCheck(Map conf) {
       

        /**
         * 安全认证相关参数校验代码
         */
        return kafkaSecurityParamCheck(conf);
    }


    /**
     * sasl 认证 参数校验
     *
     * @param conf
     * @return Map
     */
    @SuppressWarnings("all")
    private static Map saslParamCheck(Map conf) {
        return conf;
    }

    /**
     * Kerberos 参数校验
     *
     * @param conf
     * @return Map
     */
    @SuppressWarnings("all")
    private static Map kerberosParamCheck(Map conf) {
        

        return conf;
    }

    /**
     * 安全认证相关参数校验代码
     *
     * @param conf
     * @return Map
     */
    @SuppressWarnings("all")
    private static Map kafkaSecurityParamCheck(Map conf) {
        return conf;
    }
}
