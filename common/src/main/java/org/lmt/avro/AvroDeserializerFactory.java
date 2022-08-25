package org.lmt.avro;


/**
 * AvroDeserializerFactory
 *
 * @author Administrator
 */
public class AvroDeserializerFactory {
    private AvroDeserializerFactory() {
    }

    private static AvroDeserializer logs = null;
    private static AvroDeserializer metrics = null;

    /**
     * getLogsDeserializer
     *
     * @return AvroDeserializer
     */
    public static AvroDeserializer getLogsDeserializer() {
        if (null == logs) {
            logs = new AvroDeserializer(AvroSchemaDef.ZORK_LOG_SCHEMA);
        }
        return logs;
    }

    /**
     * getMetricDeserializer
     *
     * @return AvroDeserializer
     */
    public static AvroDeserializer getMetricDeserializer() {
        if (null == metrics) {
            metrics = new AvroDeserializer(AvroSchemaDef.ZORK_METRIC_SCHEMA);
        }
        return metrics;
    }
}
