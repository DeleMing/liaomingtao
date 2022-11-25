package org.lmt;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author: LiaoMingtao
 * @date: 2021/1/7
 */
public class Ctest8 {

    private static final String INPUT_AVRO_FILE_PATH = "F:\\Users\\Thinkpad\\Downloads\\info.txt";
    private static final String OUT_PUT_FILE_PATH = "F:\\申万\\日志原文\\生产环境日志收集\\零售交易类\\KCBP\\柜台类\\20220531\\runlog2.log";

    /**
     * 读取avro文件数据
     *
     * @param args
     */
    public static void main(String[] args) throws IOException {
        FileReader fr = new FileReader(OUT_PUT_FILE_PATH);
        BufferedReader br = new BufferedReader(fr);
        String line = null;
        int count = 0;
        while ((line = br.readLine()) != null ) {
            if (line.contains("[   98]")  && line.contains("Ans:")) {
                count++;
            }
        }
        System.out.println(count);
    }



}
