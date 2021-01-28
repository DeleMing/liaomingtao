package org.lmt;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * @author: LiaoMingtao
 * @date: 2021/1/27
 */
public class Ctest2 {

    private static final String INPUT_AVRO_FILE_PATH = "F:\\Users\\Thinkpad\\Downloads\\test.log";
    private static final String OUT_PUT_FILE_PATH = "F:\\Users\\Thinkpad\\Downloads\\test\\result.txt";

    public static void main(String[] args) throws Exception{
        FileReader fr = new FileReader(INPUT_AVRO_FILE_PATH);
        BufferedReader br = new BufferedReader(fr);
        String line = null;
        while ((line = br.readLine()) != null ) {
            if (line.contains("system.cpu") && line.contains("192.168.30.43") && line.contains("\"collectruleid\":8")) {
                String a = line.substring(line.indexOf("\"user\":{\"pct\":"));
                String b = a.substring(0, a.indexOf(","));
                System.out.println(b);
            }

        }
        br.close();
    }
}
