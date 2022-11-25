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
public class Ctest2 {

    private static final String A = "\"设备名称\"";
    private static final String B = "\"名称\"";
    private static final String C = "\"设备地址\"";

    public static void main(String[] args) {
        BufferedReader bufferedReader = null;
        List<String> moidList = new ArrayList<>();
        try {
            bufferedReader = new BufferedReader(new FileReader("E:\\tmp\\金证监控对接\\ES_data\\data20221121.json"));
            String line;
            int lineCount = 0;
            int aCount = 0;
            int bCount = 0;
            int cCount = 0;
            while ((line = bufferedReader.readLine()) != null) {
                lineCount++;
                if (line.contains(A)) {
                    aCount++;
                }
                if (line.contains(B)) {
                    bCount++;
                }
                if (line.contains(C)) {
                    cCount++;
                }
                Map<String, Object> map = JSON.parseObject(line, new TypeReference<Map<String, Object>>() {
                });
                Map source = MapUtils.getMap(map, "_source");
                Map extAtt = MapUtils.getMap(source, "extAtt");
                String moid = (String) extAtt.get("moid");
                if (!moidList.contains(moid)) {
                    moidList.add(moid);

                }
            }

            System.out.println("总行数:" + lineCount + "包含设备名称行数:" + aCount + "包含名称行数:" + bCount + "包含设备地址行数:" + cCount);

            FileWriter fileWriter = new FileWriter("F:\\Users\\76149\\Desktop\\moid.log");
            for (String s : moidList) {
                fileWriter.write(s + "\n");
            }
            fileWriter.close();
            System.out.println(moidList.size());
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
