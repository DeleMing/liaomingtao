package org.lmt.producer;

/**
 * @author: LiaoMingtao
 * @date: 2021/8/10
 */
public class KafkaConstants {
    public static String KAFKA_SECURITY_MODEL_SASL = "sasl";
    public static String KAFKA_SECURITY_MODEL_KERBEROS = "kerberos";
    public static String KAFKA_SECURITY_MODEL_NONE = "none";
    public static String KAFKA_SECURITY_MODEL = "kafka.security.model";
    public static String JAVA_SECURITY_KRB5_CONF = "java.security.krb5.conf";
    public static String JAVA_SECURITY_AUTH_LOGIN_CONFIG = "java.security.auth.login.config";
    public static String SECURITY_PROTOCOL = "security.protocol";
    public static String SASL_KERBEROS_SERVICE_NAME = "sasl.kerberos.service.name";
    public static String SASL_MECHANISM = "sasl.mechanism";
    public static String KAFKA_SASL_APP_KEY = "kafka.sasl.app.key";
    public static String KAFKA_SASL_SECRET_KEY = "kafka.sasl.secret.key";
    public static String KAFKA_BROKERS = "kafka.brokers";
    public static String BOOTSTRAP_SERVERS_CONFIG = "bootstrap.servers";
    public static String BATCH_SIZE = "batch.size";
    public static int KAFKA_DEFAULT_BATCH_SIZE = 100;
}
