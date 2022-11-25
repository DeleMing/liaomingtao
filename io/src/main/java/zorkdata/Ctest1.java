package zorkdata;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author: LiaoMingtao
 * @date: 2022/11/22
 */
public class Ctest1 {

    private static final String A = "\"设备名称\"";
    private static final String B = "\"名称\"";
    private static final String C = "\"设备地址\"";

    public static void main(String[] args) {
        BufferedReader bufferedReader = null;
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
            }
            System.out.println("总行数:" + lineCount + "包含设备名称行数:" + aCount + "包含名称行数:" + bCount + "包含设备地址行数:" + cCount);
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
