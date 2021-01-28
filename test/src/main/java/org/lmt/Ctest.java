package org.lmt;

import java.io.*;
import java.nio.charset.Charset;

/**
 * @author: LiaoMingtao
 * @date: 2021/1/9
 */
public class Ctest {

    private static final String LOG_PATH = "F:\\Users\\Thinkpad\\Downloads\\test\\kcbp.log";

    public static void main(String[] args) throws IOException {
        File file = new File(LOG_PATH);
        InputStream in = new FileInputStream(file);
        InputStreamReader in2 = new InputStreamReader(in, Charset.forName("GBK"));
        // BufferedReader br = new BufferedReader(new FileReader(file));
        BufferedReader br = new BufferedReader(in2);
        String line = null;
        String per = null;
        StringBuilder content = new StringBuilder();
        while (null != (line = br.readLine())) {
            content.append(line.substring(50));
            if (null == per) {
                per = line.substring(1, 15);
            }
        }
        br.close();
        // System.out.println(per);
        // System.out.println(content.toString());
        String resultMessage = per + " " + content.toString();
        System.out.println(resultMessage);
    }
}
