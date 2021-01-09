package org.lmt;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author: LiaoMingtao
 * @date: 2021/1/9
 */
public class WriteFile {

    private static final String OUT_PUT_FILE_PATH = "F:\\Users\\Thinkpad\\Downloads\\test\\result.log";


    public static void main(String[] args) throws IOException {
        // 重写文件 new FileWriter(OUT_PUT_FILE_PATH, true)
        FileWriter fileWriter = new FileWriter(OUT_PUT_FILE_PATH);
        BufferedWriter bw = new BufferedWriter(fileWriter);
        for (int i =0 ; i < 100 ; i++) {
            bw.write("ABC");
        }
        bw.close();
    }
}
