package org.lmt;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Charsets;
import com.google.common.io.Files;
import jnr.ffi.annotations.In;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;

import java.util.*;
import java.util.concurrent.*;

/**
 * @author: LiaoMingtao
 * @date: 2021/8/30
 */
public class ReadLargeFiles {

    public static void main(String[] args) {
        int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();
        int KEEP_ALIVE_TIME = 1;
        TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;
        BlockingQueue<Runnable> taskQueue = new LinkedBlockingQueue<Runnable>();
        ExecutorService executorService = new ThreadPoolExecutor(NUMBER_OF_CORES,
                10, KEEP_ALIVE_TIME, KEEP_ALIVE_TIME_UNIT,
                taskQueue);

        long start, end;
        start = System.currentTimeMillis();
        Map<String, Integer> map = new ConcurrentHashMap<>(1);
        System.out.println("开始");
        logMemory();
        File file = new File("E:\\test.log");
        try {
            InputStream is = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(is));
            String str = null;
            while (true) {
                str = reader.readLine();
                if (str == null) {
                    break;
                }
                // String finalStr = str;
                // executorService.execute(() -> {
                    spiltAndCount(str, map);
                // });

            }

            is.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.gc();
        //这里将map.entrySet()转换成list
        List<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());
        //然后通过比较器来实现排序
        //升序排序
        Collections.sort(list, (o1, o2) -> o2.getValue().compareTo(o1.getValue()));
        for (Map.Entry<String, Integer> mapping : list) {
            System.out.println(mapping.getKey() + ":" + mapping.getValue());
        }
        List<Map.Entry<String, Integer>> entries = list.subList(0, 10);
        // List<Map.Entry<String, Integer>> entries = list;
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter("E:\\result.log");
            BufferedWriter bw = new BufferedWriter(fileWriter);
            for (Map.Entry<String, Integer> entry : entries) {
                bw.write(entry.getKey() + ":" + entry.getValue() + " \n");
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("结束");
        logMemory();
        end = System.currentTimeMillis();
        System.out.println("start time:" + start + "; end time:" + end + "; Run Time:" + (end - start) + "(ms)");
    }

    private static void logMemory() {
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        //堆内存使用情况
        MemoryUsage memoryUsage = memoryMXBean.getHeapMemoryUsage();
        //初始的总内存
        long totalMemorySize = memoryUsage.getInit();
        //已使用的内存
        long usedMemorySize = memoryUsage.getUsed();
        System.out.println("Total Memory: " + totalMemorySize / (1024 * 1024) + " Mb");
        System.out.println("Free Memory: " + usedMemorySize / (1024 * 1024) + " Mb");
    }

    public static void spiltAndCount(String line, Map<String, Integer> map) {
        String regex = "\\.|\\,|\\   |\\ ";
        String[] words = line.split(regex);
        for (int i = 0; i < words.length; i++) {
            if (StringUtils.isEmpty(words[i].trim())) {
                continue;
            }
            if (map.containsKey(words[i].trim())) {
                map.put(words[i].trim(), map.get(words[i].trim()) + 1);
            } else {
                map.put(words[i].trim(), 1);
            }
        }
    }
}
