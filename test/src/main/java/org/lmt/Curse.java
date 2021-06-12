package org.lmt;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: LiaoMingtao
 * @date: 2021/5/21
 */
public class Curse {

    private static final String OUT_PUT_FILE_PATH = "F:\\Users\\Thinkpad\\Downloads\\骂人.txt";

    public static void main(String[] args) {
        String url = "https://zuanbot.com/api.php?lang=zh_cn";
        Map<String, String> header = new HashMap<>(10);
        header.put("referer", "https://zuanbot.com/");
        while (true) {
            String message = HttpClientUtil.sendHttpGet(url, header);
            try {
                FileWriter fileWriter = new FileWriter(OUT_PUT_FILE_PATH, true);
                BufferedWriter bw = new BufferedWriter(fileWriter);
                bw.write(message + "\n");
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // System.out.println(message);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
