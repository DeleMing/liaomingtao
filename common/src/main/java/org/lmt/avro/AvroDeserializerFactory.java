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

    public static void init() {
        logs = null;
        metrics = null;
    }

    /**
     * getLogsDeserializer
     *
     * @return AvroDeserializer
     */
    public static AvroDeserializer getLogsDeserializer() {
        if (logs == null) {
            logs = new AvroDeserializer(AvroSchemaDef.ZORK_LOG_SCHEMA);
        }
        return logs;
    }

    /**
     * getLogsDeserializer
     *
     * @return AvroDeserializer
     */
    public static AvroDeserializer getMetricDeserializer() {
        if (metrics == null) {
            metrics = new AvroDeserializer(AvroSchemaDef.ZORK_METRIC_SCHEMA);
        }
        return metrics;
    }
}
