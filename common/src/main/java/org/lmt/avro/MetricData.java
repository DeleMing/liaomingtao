package org.lmt.avro;

import lombok.Data;
import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator
 */
@Data
public class MetricData implements Serializable {
    /**
     * metricSetName 指标类型
     */
    private String metricSetName;
    /**
     * timestamp 时间戳
     */
    private String timestamp;
    /**
     * dimensions 维度
     */
    private Map<String, String> dimensions;
    /**
     * measures
     */
    private Map<String, Double> metrics;


    public MetricData() {
    }


    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }


    public Map<String, String> getDimensions() {
        return dimensions;
    }

    public void setDimensions(Map<String, String> dimensions) {
        this.dimensions = new HashMap<>(50);
        for (Map.Entry entry : dimensions.entrySet()) {
            this.dimensions.put(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
        }
    }

    public Map<String, Double> getMetrics() {
        return metrics;
    }

    public void setMetrics(Map<String, Double> measures) {
        this.metrics = new HashMap<>(50);
        for (Map.Entry entry : measures.entrySet()) {
            this.metrics.put(String.valueOf(entry.getKey()), Double.valueOf(String.valueOf(entry.getValue())));
        }
    }


    @Override
    public String toString() {
        return new DateTime(timestamp).toDate().getTime() + " ZorkLogData{" + "metricSetName='" + metricSetName + '\'' + ", timestamp='" + timestamp + '\'' + ", source='"
                + '\'' + ", dimensions=" + dimensions + ", measures=" + metrics
                + '}';
    }
}
