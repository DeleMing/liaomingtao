package zorkdata;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.apache.commons.collections.MapUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author: LiaoMingtao
 * @date: 2022/11/22
 */
public class Ctest3 {

    public static void main(String[] args) {
        String temp = "\"moid\":\"%s\"";
        BufferedReader bufferedReader = null;
        BufferedReader bufferedReader2 = null;
        List<String> moidList = new ArrayList<>();
        try {
            bufferedReader = new BufferedReader(new FileReader("F:\\Users\\76149\\Desktop\\moid.log"));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                moidList.add(String.format(temp, line));
            }
            bufferedReader2 = new BufferedReader(new FileReader("E:\\tmp\\金证监控对接\\rabbitmq_consumer_1\\rabbitmq_consumer.log"));
            String line2;
            int count = 0;
            int count2 = 0;
            while ((line2 = bufferedReader2.readLine()) != null) {
                count++;
                for (String s : moidList) {
                    if (line2.contains(s)) {
                        count2++;
                        break;
                    }
                }
            }
            System.out.println("总行数:" + count + "包含moid行数:" + count2);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != bufferedReader) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

    }
}
