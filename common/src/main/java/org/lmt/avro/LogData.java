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
public class LogData implements Serializable {
    /**
     * logTypeName 日志类型
     */
    private String logTypeName;
    /**
     * timestamp 时间戳
     */
    private String timestamp;
    /**
     * source
     */
    private String source;
    /**
     * offset 偏移量
     */
    private String offset;
    /**
     * dimensions 维度
     */
    private Map<String, String> dimensions;
    /**
     * measures
     */
    private Map<String, Double> measures;
    /**
     * normalFields
     */
    private Map<String, String> normalFields;


    public LogData() {
    }

    public String getLogTypeName() {
        return logTypeName;
    }

    public void setLogTypeName(String logTypeName) {
        this.logTypeName = logTypeName;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getOffset() {
        return offset;
    }

    public void setOffset(String offset) {
        this.offset = offset;
    }

    public Map<String, String> getDimensions() {
        return dimensions;
    }

    public void setDimensions(Map<String, String> dimensions) {
        this.dimensions = new HashMap<>(50);
        for (Map.Entry<String, String> entry : dimensions.entrySet()) {
            this.dimensions.put(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
        }
    }

    public Map<String, Double> getMeasures() {
        return measures;
    }

    public void setMeasures(Map<String, Double> measures) {
        this.measures = new HashMap<>(50);
        for (Map.Entry<String, Double> entry : measures.entrySet()) {
            this.measures.put(String.valueOf(entry.getKey()), Double.valueOf(String.valueOf(entry.getValue())));
        }
    }

    public Map<String, String> getNormalFields() {
        return normalFields;
    }

    public void setNormalFields(Map<String, String> normalFields) {
        this.normalFields = new HashMap<>(50);
        for (Map.Entry<String, String> entry : normalFields.entrySet()) {
            this.normalFields.put(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
        }
    }

    @Override
    public String toString() {
        return new DateTime(timestamp).toDate().getTime() + " ZorkLogData{" + "logTypeName='" + logTypeName + '\'' + ", timestamp='" + timestamp + '\'' + ", source='"
                + source + '\'' + ", offset='" + offset + '\'' + ", dimensions=" + dimensions + ", measures=" + measures
                + ", normalFields=" + normalFields + '}';
    }
}
