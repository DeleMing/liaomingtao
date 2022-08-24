package org.lmt.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.io.Serializable;
import java.util.Map;
import java.util.Properties;

/**
 * @author: LiaoMingtao
 * @date: 2021/8/10
 */
@Slf4j
public class KafkaTools implements Serializable {
    private static String kafkaSecurityModel;

    public static KafkaProducer<String, String> getStrProducer(Map<String, Object> conf) {
        Properties props = getKafkaProperties(conf);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        return new KafkaProducer<>(props);
    }

    public static KafkaProducer<String, byte[]> getAvroProducer(Map<String, Object> conf) {
        Properties props = getKafkaProperties(conf);
        return new KafkaProducer<>(props);
    }

    /**
     * 获取 kafka Properties
     *
     * @param conf Map<String, Object>
     * @return Properties
     */
    private static Properties getKafkaProperties(Map<String, Object> conf) {
        conf = paramCheck(conf);
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, conf.get(KafkaConstants.BOOTSTRAP_SERVERS_CONFIG));
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class.getName());
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, Integer.valueOf(String.valueOf(conf.get(KafkaConstants.BATCH_SIZE))));
        if (KafkaConstants.KAFKA_SECURITY_MODEL.equalsIgnoreCase(kafkaSecurityModel)) {
            System.setProperty(KafkaConstants.JAVA_SECURITY_KRB5_CONF, String.valueOf(conf.get(KafkaConstants.JAVA_SECURITY_KRB5_CONF)));
            System.setProperty(KafkaConstants.JAVA_SECURITY_AUTH_LOGIN_CONFIG, String.valueOf(conf.get(KafkaConstants.JAVA_SECURITY_AUTH_LOGIN_CONFIG)));
            props.put(KafkaConstants.SECURITY_PROTOCOL, conf.get(KafkaConstants.SECURITY_PROTOCOL));
            props.put(KafkaConstants.SASL_KERBEROS_SERVICE_NAME, conf.get(KafkaConstants.SASL_KERBEROS_SERVICE_NAME));
            props.put(KafkaConstants.SASL_MECHANISM, conf.get(KafkaConstants.SASL_MECHANISM));
        } else if (KafkaConstants.KAFKA_SECURITY_MODEL_SASL.equalsIgnoreCase(kafkaSecurityModel)) {
            props.put(KafkaConstants.SECURITY_PROTOCOL, conf.get(KafkaConstants.SECURITY_PROTOCOL));
            props.put(KafkaConstants.SASL_MECHANISM, conf.get(KafkaConstants.SASL_MECHANISM));
            props.put(SaslConfigs.SASL_JAAS_CONFIG, "org.apache.kafka.common.security.plain.PlainLoginModule required "
                    + "username=\"" + conf.get(KafkaConstants.KAFKA_SASL_APP_KEY) + "\" "
                    + "password=\"" + conf.get(KafkaConstants.KAFKA_SASL_SECRET_KEY) + "\";");
        }
        return props;
    }

    /**
     * kafka producer 参数校验
     *
     * @param conf Map<String, Object>
     * @return Map
     */
    private static Map<String, Object> paramCheck(Map<String, Object> conf) {
        if (!conf.containsKey(KafkaConstants.BOOTSTRAP_SERVERS_CONFIG)) {
            log.error("配置参数 [{}] 不能为空", KafkaConstants.BOOTSTRAP_SERVERS_CONFIG);
            throw new RuntimeException("配置参数 [ " + KafkaConstants.BOOTSTRAP_SERVERS_CONFIG + " ] 不能为空");
        }

        if (!conf.containsKey(KafkaConstants.BATCH_SIZE)) {
            log.warn("配置参数 [{}] 为空,使用默认值 [{}]", KafkaConstants.BATCH_SIZE, KafkaConstants.KAFKA_DEFAULT_BATCH_SIZE);
            conf.put(ProducerConfig.BATCH_SIZE_CONFIG, KafkaConstants.KAFKA_DEFAULT_BATCH_SIZE);
        }
        return kafkaSecurityParamCheck(conf);
    }


    /**
     * sasl 认证 参数校验
     *
     * @param conf Map<String, Object>
     * @return Map
     */
    private static Map<String, Object> saslParamCheck(Map<String, Object> conf) {
        if (!conf.containsKey(KafkaConstants.SECURITY_PROTOCOL)) {
            log.error("配置参数 [ {} ] 不能为空", KafkaConstants.SECURITY_PROTOCOL);
            throw new RuntimeException("配置参数 [ " + KafkaConstants.SECURITY_PROTOCOL + " ] 不能为空");
        }

        if (!conf.containsKey(KafkaConstants.SASL_MECHANISM)) {
            log.error("配置参数 [ {} ] 不能为空", KafkaConstants.SASL_MECHANISM);
            throw new RuntimeException("配置参数 [ " + KafkaConstants.SASL_MECHANISM + " ] 不能为空");
        }
        if (!conf.containsKey(KafkaConstants.KAFKA_SASL_APP_KEY)) {
            log.error("配置参数 [ {} ] 不能为空", KafkaConstants.KAFKA_SASL_APP_KEY);
            throw new RuntimeException("配置参数 [ " + KafkaConstants.KAFKA_SASL_APP_KEY + " ] 不能为空");
        }

        if (!conf.containsKey(KafkaConstants.KAFKA_SASL_SECRET_KEY)) {
            log.error("配置参数 [ {} ] 不能为空", KafkaConstants.KAFKA_SASL_SECRET_KEY);
            throw new RuntimeException("配置参数 [ " + KafkaConstants.KAFKA_SASL_SECRET_KEY + " ] 不能为空");
        }
        return conf;
    }

    /**
     * Kerberos 参数校验
     *
     * @param conf Map<String, Object>
     * @return Map
     */
    private static Map<String, Object> kerberosParamCheck(Map<String, Object> conf) {
        if (!conf.containsKey(KafkaConstants.JAVA_SECURITY_KRB5_CONF)) {
            log.error("配置参数 [" + KafkaConstants.JAVA_SECURITY_KRB5_CONF + "] 不能为空");
            throw new RuntimeException("配置参数 [" + KafkaConstants.JAVA_SECURITY_KRB5_CONF + "] 不能为空");
        }
        if (!conf.containsKey(KafkaConstants.JAVA_SECURITY_AUTH_LOGIN_CONFIG)) {
            log.error("配置参数 [" + KafkaConstants.JAVA_SECURITY_AUTH_LOGIN_CONFIG + "] 不能为空");
            throw new RuntimeException("配置参数 [" + KafkaConstants.JAVA_SECURITY_AUTH_LOGIN_CONFIG + "] 不能为空");
        }
        if (!conf.containsKey(KafkaConstants.SECURITY_PROTOCOL)) {
            log.error("配置参数 [" + KafkaConstants.SECURITY_PROTOCOL + "] 不能为空");
            throw new RuntimeException("配置参数 [" + KafkaConstants.SECURITY_PROTOCOL + "] 不能为空");
        }
        if (!conf.containsKey(KafkaConstants.SASL_KERBEROS_SERVICE_NAME)) {
            log.error("配置参数 [" + KafkaConstants.SASL_KERBEROS_SERVICE_NAME + "] 不能为空");
            throw new RuntimeException("配置参数 [" + KafkaConstants.SASL_KERBEROS_SERVICE_NAME + "] 不能为空");
        }
        if (!conf.containsKey(KafkaConstants.SASL_MECHANISM)) {
            log.error("配置参数 [" + KafkaConstants.SASL_MECHANISM + "] 不能为空");
            throw new RuntimeException("配置参数 [" + KafkaConstants.SASL_MECHANISM + "] 不能为空");
        }

        return conf;
    }

    /**
     * 安全认证相关参数校验代码
     *
     * @param conf 配置
     * @return Map<String, Object>
     */
    private static Map<String, Object> kafkaSecurityParamCheck(Map<String, Object> conf) {
        if (conf.containsKey(KafkaConstants.KAFKA_SECURITY_MODEL)) {
            kafkaSecurityModel = String.valueOf(conf.get(KafkaConstants.KAFKA_SECURITY_MODEL));
            if (KafkaConstants.KAFKA_SECURITY_MODEL_SASL.equalsIgnoreCase(kafkaSecurityModel)) {
                return saslParamCheck(conf);
            } else if (KafkaConstants.KAFKA_SECURITY_MODEL_KERBEROS.equalsIgnoreCase(kafkaSecurityModel)) {
                return kerberosParamCheck(conf);
            }
        }
        return conf;
    }
}
