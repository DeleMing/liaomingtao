package org.lmt;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import java.io.*;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;
import java.util.stream.Stream;

/**
 * @author: LiaoMingtao
 * @date: 2021/8/30
 */
public class ReadMaxFile {

    private static Map<String, Integer> map = new ConcurrentHashMap<>();

    @SneakyThrows
    public static void main(String[] args) {
        // 先将大文件拆分成小文件
        List<File> fileList = splitLargeFile("E:\\test.log");
        // 创建一个 最大线程数为 10，队列最大数为 100 的线程池
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 10, 60l, TimeUnit.SECONDS, new LinkedBlockingDeque<>(100));
        List<Future> futureList = Lists.newArrayList();
        for (File file : fileList) {
            Future<?> future = threadPoolExecutor.submit(() -> {
                FileReader fr = null;
                try {
                    fr = new FileReader(file);
                    BufferedReader br = new BufferedReader(fr);
                    String line = null;
                    while ((line = br.readLine()) != null) {
                        spiltAndCount(line, map);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            });
            futureList.add(future);
        }
        for (Future future : futureList) {
            // 等待所有任务执行结束
            future.get();
        }
        threadPoolExecutor.shutdown();
        System.out.println(JSON.toJSONString(map));
    }

    @SneakyThrows
    private static void createSmallFile(List<String> lines, int num, List<File> files) {
        // 重写文件 new FileWriter(OUT_PUT_FILE_PATH, true)
        FileWriter fileWriter = new FileWriter("E:\\test\\" + num + ".log");
        BufferedWriter bw = new BufferedWriter(fileWriter);
        for (String line : lines) {
            bw.write(line);
        }
        bw.close();
        File file = new File("E:\\test\\" + num + ".log");
        files.add(file);
    }

    private static List<File> splitLargeFile(String largeFileName) throws IOException {
        Stopwatch stopwatch = Stopwatch.createStarted();
        LineIterator fileContents = FileUtils.lineIterator(new File(largeFileName), StandardCharsets.UTF_8.name());
        List<String> lines = Lists.newArrayList();
        // 文件序号
        int num = 1;
        List<File> files = Lists.newArrayList();
        while (fileContents.hasNext()) {
            String nextLine = fileContents.nextLine();
            lines.add(nextLine);
            // 每个文件 10w 行数据
            if (lines.size() == 10000) {
                createSmallFile(lines, num, files);
                num++;
                System.gc();
            }
        }
        stopwatch.stop();
        // 计算内存占用
        logMemory();
        // lines 若还有剩余，继续执行结束
        if (!lines.isEmpty()) {
            // 继续执行
            createSmallFile(lines, num, files);
        }
        return files;
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

    // @SneakyThrows
    // public static void main(String[] args) throws IOException {
    //     // 创建一个 最大线程数为 10，队列最大数为 100 的线程池
    //     ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 10, 60L, TimeUnit.SECONDS, new LinkedBlockingDeque<>(100));
    //     // 使用 Apache 的方式逐行读取数据
    //     LineIterator fileContents = FileUtils.lineIterator(new File("E:\\test.log"), StandardCharsets.UTF_8.name());
    //     List<String> lines = Lists.newArrayList();
    //     while (fileContents.hasNext()) {
    //         String nextLine = fileContents.nextLine();
    //         lines.add(nextLine);
    //         // 读取到十万的时候
    //         if (lines.size() == 1000) {
    //             // 拆分成两个 50000 ，交给异步线程处理
    //             List<List<String>> partition = Lists.partition(lines, 50000);
    //             List<Future> futureList = Lists.newArrayList();
    //             for (List<String> strings : partition) {
    //                 Future<?> future = threadPoolExecutor.submit(() -> {
    //                     processTask(strings);
    //                 });
    //                 futureList.add(future);
    //             }
    //             // 等待两个线程将任务执行结束之后，再次读取数据。这样的目的防止，任务过多，加载的数据过多，导致 OOM
    //             for (Future future : futureList) {
    //                 // 等待执行结束
    //                 future.get();
    //             }
    //             // 清除内容
    //             lines.clear();
    //         }
    //
    //
    //     }
    //     // lines 若还有剩余，继续执行结束
    //     if (!lines.isEmpty()) {
    //         // 继续执行
    //         processTask(lines);
    //     }
    //     threadPoolExecutor.shutdown();
    //     JSONObject.toJSONString(map);
    // }

    private static void processTask(List<String> strings) {
        for (String line : strings) {
            // 模拟业务执行
            try {
                spiltAndCount(line, map);
                TimeUnit.MILLISECONDS.sleep(10L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //找到目标文件,创建字符输入流对象，
    public static Reader findFile() {
        File f = new File("E:\\test.log");
        Reader in = null;
        try {
            in = new FileReader(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return in;
    }

    //缓存流
    public static BufferedReader inputPipe(Reader in) {
        BufferedReader br = null;
        br = new BufferedReader(in);
        return br;
    }

    //读取文章内容
    public static String readAll(BufferedReader br, Reader in) {
        String str;
        Map<String, Integer> map = new HashMap<>();
        StringBuilder words = null;
        String allwords = null;
        try {
            StringBuilder sb = new StringBuilder();
            while ((str = br.readLine()) != null) {

                words = sb.append(str);
                allwords = sb.toString();
            }
            br.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return allwords;
    }

    //将文章以非单词字符隔开,并存储在map键值对中，并输出
    public static void spiltAndCount(String allwords, Map<String, Integer> map) {
        String regex = "\\W+";/*/[\s]|[ ]|[,]|[.]|[“”]|[?]|[  ]*/
        String[] words = allwords.split(regex);
        for (int i = 0; i < words.length; i++) {
            if (map.containsKey(words[i])) {
                map.put(words[i], map.get(words[i]) + 1);
            } else {
                map.put(words[i], 1);
            }
        }
        Set<String> keys = map.keySet();

        for (String key : keys) {
            System.out.println(key + "---->" + map.get(key));
        }
    }

}
