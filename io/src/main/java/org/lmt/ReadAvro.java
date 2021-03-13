package org.lmt;

import org.apache.avro.Schema;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;
import org.lmt.avro.AvroSchemaDef;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: LiaoMingtao
 * @date: 2021/1/9
 */
public class ReadAvro {

    private static final String INPUT_AVRO_FILE_PATH = "F:\\Users\\Thinkpad\\Downloads\\avro-tools\\part-0-1.avro";
    private static final String OUT_PUT_FILE_PATH = "F:\\Users\\Thinkpad\\Downloads\\avro-tools\\result.txt";


    public static void main(String[] args) throws IOException {
        List<String> avroStringList = new ArrayList<>();
        Schema schema = new Schema.Parser().parse(AvroSchemaDef.ZORK_LOG_SCHEMA);
        DatumReader<GenericRecord> dr = new GenericDatumReader<>(schema);
        File file = new File(INPUT_AVRO_FILE_PATH);

        DataFileReader<GenericRecord> dfr = new DataFileReader(file, dr);
        int count = 0;
        while (dfr.hasNext()) {
            count ++;
            avroStringList.add(dfr.next().toString());
        }
        dfr.close();
        System.out.println(count);
        BufferedWriter bw = new BufferedWriter(new FileWriter(OUT_PUT_FILE_PATH));
        for (String s : avroStringList) {
            bw.write(s + "\n");
        }
        bw.close();
        int count2 = 0;
        BufferedReader br = new BufferedReader(new FileReader(OUT_PUT_FILE_PATH));
        String line = null;
        while((line = br.readLine())!= null) {
            count2++;
        }
        System.out.println(count2);
        br.close();
    }
}
