package org.lmt.avro;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.util.Utf8;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * avro 序列化
 *
 * @author Administrator
 */
@Slf4j
public class AvroSerializer {
    private static final String LOG_TYPE_NAME = "logTypeName";
    private static final String TIMESTAMP = "timestamp";
    private static final String SOURCE = "source";
    private static final String OFFSET = "offset";
    private static final String DIMENSIONS = "dimensions";
    private static final String MEASURES = "measures";
    private static final String NORMAL_FIELDS = "normalFields";
    private static final String METRIC_SET_NAME = "metricsetname";
    private static final String METRICS = "metrics";
    private JSONObject jsonObject;
    private JSONArray jsonArray;
    private Schema schema;
    private List<String> fieldsArrayList = new ArrayList<>();

    public AvroSerializer(String schema) {
        this.jsonObject = JSONObject.parseObject(schema);
        this.schema = new Schema.Parser().parse(schema);
        this.jsonArray = this.jsonObject.getJSONArray("fields");
        if (!fieldsArrayList.isEmpty()) {
            fieldsArrayList.clear();
        }
        for (int i = 0; i < this.jsonArray.size(); i++) {
            fieldsArrayList.add(this.jsonArray.getJSONObject(i).get("name").toString());
        }
    }


    /**
     * 用于Avro的序列化
     *
     * @param tempTuple 字符串数组
     * @return byte数组
     */
    public synchronized byte[] serializing(List<String> tempTuple) {
        byte[] returnstr = null;
        GenericRecord datum = new GenericData.Record(this.schema);
        // 将数据加到datum中
        for (int i = 0; i < fieldsArrayList.size(); i++) {
            datum.put(fieldsArrayList.get(i), tempTuple.get(i));
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        // DatumWriter 将数据对象翻译成Encoder对象可以理解的类型
        DatumWriter<GenericRecord> write = new GenericDatumWriter<>(this.schema);
        // 然后由Encoder写到数据流。
        Encoder encoder = EncoderFactory.get().binaryEncoder(out, null);
        try {
            write.write(datum, encoder);
            encoder.flush();

        } catch (IOException e) {
            log.error("序列化失败", e);
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                log.error("序列化失败", e);
            }
        }
        returnstr = out.toByteArray();
        return returnstr;
    }

    /**
     * 序列化json串
     *
     * @param json
     * @return
     */
    public synchronized byte[] serializing(String json) {
        byte[] returnstr = null;
        JSONObject jsonObject = (JSONObject) JSONObject.parse(json);
        GenericRecord datum = new GenericData.Record(this.schema);
        // 将数据加到datum中
        for (int i = 0; i < fieldsArrayList.size(); i++) {
            datum.put(fieldsArrayList.get(i), new Utf8(String.valueOf(jsonObject.get(fieldsArrayList.get(i)))));
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        // DatumWriter 将数据对象翻译成Encoder对象可以理解的类型
        DatumWriter<GenericRecord> write = new GenericDatumWriter<>(this.schema);
        // 然后由Encoder写到数据流。
        Encoder encoder = EncoderFactory.get().binaryEncoder(out, null);

        try {
            write.write(datum, encoder);
            encoder.flush();
        } catch (IOException e) {
            log.error("序列化失败", e);
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                log.error("序列化失败", e);
            }
        }
        returnstr = out.toByteArray();
        return returnstr;
    }

    /**
     * 序列化json对象
     *
     * @param jsonObject
     * @return
     */
    public synchronized byte[] serializing(JSONObject jsonObject) {
        byte[] returnstr = null;
        GenericRecord datum = new GenericData.Record(this.schema);
        // 将数据加到datum中
        for (int i = 0; i < fieldsArrayList.size(); i++) {
            datum.put(fieldsArrayList.get(i), jsonObject.get(fieldsArrayList.get(i)));
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        // DatumWriter 将数据对象翻译成Encoder对象可以理解的类型
        DatumWriter<GenericRecord> write = new GenericDatumWriter<GenericRecord>(this.schema);
        // 然后由Encoder写到数据流。
        Encoder encoder = EncoderFactory.get().binaryEncoder(out, null);

        try {
            write.write(datum, encoder);
            encoder.flush();
        } catch (IOException e) {
            log.error("序列化失败", e);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    log.error("序列化失败", e);
                }
            }
        }
        try {
            returnstr = out.toByteArray();
        } catch (Exception e) {
            log.error("序列化失败", e);
        }
        return returnstr;
    }

    /**
     * 序列化对象
     */
    public synchronized byte[] serializing(GenericRecord datum) {
        byte[] returnstr = null;

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        // DatumWriter 将数据对象翻译成Encoder对象可以理解的类型 
        DatumWriter<GenericRecord> write = new GenericDatumWriter<GenericRecord>(this.schema);
        // 然后由Encoder写到数据流。
        Encoder encoder = EncoderFactory.get().binaryEncoder(out, null);

        try {
            write.write(datum, encoder);
            encoder.flush();
        } catch (IOException e) {
            log.error("序列化失败", e);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    log.error("序列化失败", e);
                }
            }
        }
        try {
            returnstr = out.toByteArray();
        } catch (Exception e) {
            log.error("序列化失败", e);
        }

        // GenericRecord s = AvroDeserializerFactory.getTopicmetadataDeserializer().deserializing(returnstr);
        return returnstr;
    }

    /**
     * 序列化对象
     */
    public synchronized byte[] serializingLog(String logTypeName, String timestamp, String source, String offset, Map<String, String> dimensions, Map<String, Double> metrics,
                                              Map<String, String> normalFields) {
        GenericRecord datum = new GenericData.Record(this.schema);
        // 将数据加到datum中
        datum.put(0, logTypeName);
        datum.put(1, timestamp);
        datum.put(2, source);
        datum.put(3, offset);
        datum.put(4, dimensions);
        datum.put(5, metrics);
        datum.put(6, normalFields);

        return serializing(datum);
    }

    /**
     * 序列化对象
     */
    public synchronized byte[] serializingMetric(String metricSetName, String timestamp, Map<String, String> dimensions, Map<String, Double> metrics) {
        GenericRecord datum = new GenericData.Record(this.schema);
        // 将数据加到datum中
        datum.put(0, metricSetName);
        datum.put(1, timestamp);
        datum.put(2, dimensions);
        datum.put(3, metrics);

        return serializing(datum);
    }

    public synchronized byte[] serializing(GenericRecord genericRecord, String key[]) {
        byte[] returnstr = null;
        GenericRecord datum = new GenericData.Record(this.schema);
        // 将数据加到datum中
        for (int i = 0; i < fieldsArrayList.size(); i++) {
            datum.put(fieldsArrayList.get(i), new Utf8(String.valueOf(genericRecord.get(key[i]))));
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        // DatumWriter 将数据对象翻译成Encoder对象可以理解的类型
        DatumWriter<GenericRecord> write = new GenericDatumWriter<GenericRecord>(this.schema);
        // 然后由Encoder写到数据流。
        Encoder encoder = EncoderFactory.get().binaryEncoder(out, null);

        try {
            write.write(datum, encoder);
            encoder.flush();

        } catch (IOException e) {
            log.error("序列化失败", e);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    log.error("序列化失败", e);
                }
            }
        }
        try {
            returnstr = out.toByteArray();
        } catch (Exception e) {
            log.error("序列化失败", e);
        }
        return returnstr;
    }

    public synchronized byte[] serializingLog(Map<String, Object> bigMap) {
        GenericRecord datum = new GenericData.Record(this.schema);
        // 将数据加到datum中
        datum.put(0, String.valueOf(bigMap.get(LOG_TYPE_NAME)));
        datum.put(1, String.valueOf(bigMap.get(TIMESTAMP)));
        datum.put(2, String.valueOf(bigMap.get(SOURCE)));
        datum.put(3, String.valueOf(bigMap.get(OFFSET)));
        Map<String, Object> dimensions = (Map<String, Object>) bigMap.get(DIMENSIONS);
        Map<String, String> dimensionsStr = new HashMap<>(8);
        if (dimensions != null) {
            for (String key : dimensions.keySet()) {
                Object object = dimensions.get(key);
                if (object instanceof JSONObject) {
                    dimensionsStr.put(key, String.valueOf(object));
                } else {
                    dimensionsStr.put(key, String.valueOf(object));
                }
            }
        }
        datum.put(4, dimensionsStr);

        try {
            Map<String, Object> measures = (Map<String, Object>) bigMap.get(MEASURES);
            Map<String, Double> measuresStr = new HashMap<>(1);
            if (measures != null) {
                for (String key : measures.keySet()) {
                    Object object = measures.get(key);
                    if (object instanceof JSONObject) {
                        measuresStr.put(key, Double.parseDouble(String.valueOf(measures.get(key))));
                    } else {
                        measuresStr.put(key, Double.parseDouble(String.valueOf(measures.get(key))));
                    }
                }
            }
            datum.put(5, measuresStr);
        } catch (Exception e) {
            datum.put(5, new HashMap<>(1));
        }


        Map<String, String> normalFieldsStr = new HashMap<>(1);
        Map<String, Object> normalFields = (Map<String, Object>) bigMap.get(NORMAL_FIELDS);
        if (normalFields != null) {
            for (String key : normalFields.keySet()) {
                Object object = normalFields.get(key);
                if (object instanceof JSONObject) {
                    normalFieldsStr.put(key, String.valueOf(object));
                } else {
                    normalFieldsStr.put(key, String.valueOf(object));
                }
            }
        }
        datum.put(6, normalFieldsStr);

        return serializing(datum);
    }


    /**
     * 将BigDecimal value 转为 double value，防止avro序列化时报错：类型转换异常
     *
     * @param map value为BigDecimal类型的map
     * @return value为Double类型的map
     */
    private Map<String, Double> bigDecimal2Double(Map<String, BigDecimal> map) {
        Map<String, Double> result = new HashMap<>(8);
        for (Map.Entry<String, BigDecimal> entry : map.entrySet()) {
            result.put(entry.getKey(), entry.getValue().doubleValue());
        }
        return result;
    }

    public synchronized byte[] serializingMetric(Map<String, Object> bigMap) {
        GenericRecord datum = new GenericData.Record(this.schema);
        // 将数据加到datum中
        datum.put(0, bigMap.get(METRIC_SET_NAME));
        datum.put(1, bigMap.get(TIMESTAMP));
        datum.put(2, bigMap.get(DIMENSIONS));
        HashMap<String, Double> hashMap = new HashMap<>(100);
        if (bigMap.containsKey(MEASURES)) {
            Map<String, Long> map = (Map<String, Long>) bigMap.get(MEASURES);
            for (Map.Entry<String, Long> entry : map.entrySet()) {
                Double value = Double.valueOf(String.valueOf(entry.getValue()));
                hashMap.put(entry.getKey(), value);
            }
            datum.put(3, hashMap);
        } else {
            Map<String, Long> map = (Map<String, Long>) bigMap.get(METRICS);
            for (Map.Entry<String, Long> entry : map.entrySet()) {
                Double value = Double.valueOf(String.valueOf(entry.getValue()));
                hashMap.put(entry.getKey(), value);
            }
            datum.put(3, hashMap);
        }
        return serializing(datum);
    }
}
