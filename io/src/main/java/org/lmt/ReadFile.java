package org.lmt;

import org.apache.avro.Schema;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;
import org.lmt.avro.AvroDeserializer;
import org.lmt.avro.AvroDeserializerFactory;
import org.lmt.avro.AvroSchemaDef;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: LiaoMingtao
 * @date: 2021/1/7
 */
public class ReadFile {

    private static final String INPUT_AVRO_FILE_PATH = "F:\\Users\\Thinkpad\\Downloads\\info.txt";
    private static final String OUT_PUT_FILE_PATH = "E:\\test.log";

    /**
     * 读取avro文件数据
     *
     * @param args
     */
    public static void main(String[] args) throws IOException {
        FileReader fr = new FileReader(OUT_PUT_FILE_PATH);
        BufferedReader br = new BufferedReader(fr);
        String line = null;
        while ((line = br.readLine()) != null ) {
            System.out.println(line);
        }
    }



}
