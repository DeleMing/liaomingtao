package org.lmt.avro;


/**
 * AvroSerializerFactory
 *
 * @author Administrator
 */
public class AvroSerializerFactory {
    private AvroSerializerFactory() {

    }

    private static AvroSerializer metricMetadata = null;
    private static AvroSerializer logMetadata = null;

    /**
     * 获取日志avro结构
     *
     * @return AvroSerializer
     */
    public static AvroSerializer getLogAvroSerializer() {
        if (logMetadata == null) {
            logMetadata = new AvroSerializer(AvroSchemaDef.ZORK_LOG_SCHEMA);
        }
        return logMetadata;
    }

    /**
     * 获取指标avro结构
     *
     * @return AvroSerializer
     */
    public static AvroSerializer getMetricAvroSerializer() {
        if (metricMetadata == null) {
            metricMetadata = new AvroSerializer(AvroSchemaDef.ZORK_METRIC_SCHEMA);
        }
        return metricMetadata;
    }
}
