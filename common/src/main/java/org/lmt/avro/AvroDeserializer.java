package org.lmt.avro;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;

import java.io.IOException;

/**
 * avro 数据反序列化
 *
 * @author Administrator
 */
@Slf4j
public class AvroDeserializer {

    public static final String FIELDS = "fields";
    public static final String NAME = "name";

    private final Schema schema;
    private String[] keys;
    private JSONObject jsonObject;
    private JSONArray jsonArray;

    public AvroDeserializer(String schema) {
        this.schema = new Schema.Parser().parse(schema);
        this.jsonObject = JSONObject.parseObject(schema);
        this.jsonArray = jsonObject.getJSONArray(FIELDS);
        this.keys = new String[jsonArray.size()];
        for (int i = 0; i < jsonArray.size(); i++) {
            keys[i] = jsonArray.getJSONObject(i).get(NAME).toString();
        }
    }

    /**
     * Avro 的反序列化
     *
     * @param body 数：byte[] body：kafka消息
     * @return GenericRecord
     */
    public GenericRecord deserialize(byte[] body) {
        DatumReader<GenericData.Record> datumReader = new GenericDatumReader<>(this.schema);
        Decoder decoder = DecoderFactory.get().binaryDecoder(body, null);
        GenericData.Record result = null;
        try {
            result = datumReader.read(null, decoder);
        } catch (IOException e) {
            log.error("avro 反序列化失败", e);

        }
        return result;
    }

}
