package org.lmt;

import com.alibaba.fastjson.JSON;
import org.apache.avro.Schema;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;
import org.lmt.avro.AvroSchemaDef;
import org.lmt.avro.LogData;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author: LiaoMingtao
 * @date: 2021/1/9
 */
public class Ctest {

    private static final String INPUT_AVRO_FILE_PATH = "F:\\Users\\Thinkpad\\Downloads\\part-0-0.avro";
    private static final String OUT_PUT_FILE_PATH = "F:\\Users\\Thinkpad\\Downloads\\test\\result.txt";

    public static void main(String[] args) throws IOException {
        List<String> avroStringList = new ArrayList<>();
        Schema schema = new Schema.Parser().parse(AvroSchemaDef.ZORK_LOG_SCHEMA);
        DatumReader<GenericRecord> dr = new GenericDatumReader<>(schema);
        File file = new File(INPUT_AVRO_FILE_PATH);

        DataFileReader<GenericRecord> dfr = new DataFileReader(file, dr);
        int count = 0;
        while (dfr.hasNext()) {
            count++;
            avroStringList.add(dfr.next().toString());
        }
        dfr.close();
        System.out.println("avro 数据条数" + count);


        BufferedWriter bw = new BufferedWriter(new FileWriter(OUT_PUT_FILE_PATH));
        for (String s : avroStringList) {
            if (null != s) {
                LogData logData = JSON.parseObject(s, LogData.class);
                Map<String, String> normalFields = logData.getNormalFields();
                if (normalFields.containsKey("message")) {
                    bw.write(normalFields.get("message").toString() + "\n");
                }
                System.out.print("普通列有：" + logData.getNormalFields().values().size());
                System.out.print("指标列有：" + logData.getMeasures().values().size());
                System.out.println("维度列有：" + logData.getDimensions().values().size());
            }
        }
        bw.close();
        int count2 = 0;
        BufferedReader br = new BufferedReader(new FileReader(OUT_PUT_FILE_PATH));
        String line = null;
        while ((line = br.readLine()) != null) {
            System.out.println(line);
            count2++;
        }
        System.out.println("json写入文件之后数据条数" + count2);
        br.close();
    }

}
