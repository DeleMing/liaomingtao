package org.lmt;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author: LiaoMingtao
 * @date: 2021/1/9
 */
public class WriteFile {

    private static final String OUT_PUT_FILE_PATH = "E:\\test1.log";


    public static void main(String[] args) throws IOException {
        // 重写文件 new FileWriter(OUT_PUT_FILE_PATH, true)
        FileWriter fileWriter = new FileWriter(OUT_PUT_FILE_PATH);
        BufferedWriter bw = new BufferedWriter(fileWriter);
        String a = "Our current preoccupation with zombies and vampires is easy to explain. They're two sides of the same coin, addressing our fascination with sex, death and food.   ";
        System.out.println(a.getBytes(StandardCharsets.UTF_8).length);
        // long len = 65873731L;
        long len = 658737L;
        // long len = 100L;        // long len = 65873731L;

        for (long i = 0L; i < len; i++) {
            bw.write("Our current preoccupation with zombies and vampires is easy to explain. They're two sides of the same coin, addressing our fascination with sex, death and food.   \n");
        }
        bw.close();
    }
}