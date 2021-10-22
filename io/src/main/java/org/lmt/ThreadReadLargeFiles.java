package org.lmt;

import com.alibaba.fastjson.JSON;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

/**
 * @author: LiaoMingtao
 * @date: 2021/8/31
 */
public class ThreadReadLargeFiles {
    /**
     * 线程数
     */
    public static final int THREAD_POOL_SIZE = 16;

    public static void main(String[] args) {
        long start, end;
        start = System.currentTimeMillis();

        // 使用 ThreadFactoryBuilder 创建自定义线程名称的 ThreadFactory
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("read-files-pool-%d").build();

        // 创建线程池，其中任务队列需要结合实际情况设置合理的容量
        ThreadPoolExecutor executor = new ThreadPoolExecutor(THREAD_POOL_SIZE,
                THREAD_POOL_SIZE,
                0L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(1024),
                namedThreadFactory,
                new ThreadPoolExecutor.CallerRunsPolicy());
        try {
            FileInputStream fis = new FileInputStream(new File("E:\\test.log"));
            InputStreamReader is = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(is);
            List<Future<Map<String, Integer>>> futureList = new ArrayList<>();
            StringBuilder builder = new StringBuilder();
            String line;
            int i = 0;
            while (null != (line = br.readLine())) {
                if (i >= 1000) {
                    i = 0;
                    Future<Map<String, Integer>> future = executor.submit(new WordCount(builder.toString()));
                    futureList.add(future);
                    builder = new StringBuilder();
                }
                builder.append(line).append(" ");
                i++;
            }
            if (builder.length() > 0
                    && !"null".equals(builder.toString())
                    && !"".equals(builder.toString())) {
                Future<Map<String, Integer>> future = executor.submit(new WordCount(builder.toString()));
                futureList.add(future);
            }
            Map<String, Integer> resultMap = new HashMap<>(1024);
            for (Future<Map<String, Integer>> mapFuture : futureList) {
                try {
                    Map<String, Integer> countMap = mapFuture.get();
                    mergeMap(resultMap, countMap);
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
            Map<String, Integer> stringIntegerMap = sortDescend(resultMap);
            System.out.println(JSON.toJSONString(stringIntegerMap));
            br.close();
            is.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        executor.shutdown();
        end = System.currentTimeMillis();
        System.out.println("start time:" + start + "; end time:" + end + "; Run Time:" + (end - start) + "(ms)");
    }

    /**
     * map降序排列
     *
     * @param map 排序map
     * @param <K> key
     * @param <V> value
     * @return 排序后map
     */
    public static <K, V extends Comparable<? super V>> Map<K, V> sortDescend(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
            @Override
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                int compare = (o1.getValue()).compareTo(o2.getValue());
                return -compare;
            }
        });

        Map<K, V> returnMap = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            returnMap.put(entry.getKey(), entry.getValue());
        }
        return returnMap;
    }

    /**
     * map合并
     *
     * @param resultMap 合并的目标map
     * @param sourceMap 需要合并的map
     * @return
     */
    public static Map<String, Integer> mergeMap(Map<String, Integer> resultMap, Map<String, Integer> sourceMap) {
        Object[] os = sourceMap.keySet().toArray();
        String key;
        for (int i = 0; i < os.length; i++) {
            key = (String) os[i];
            if (resultMap.containsKey(key)) {
                resultMap.put(key, resultMap.get(key) + sourceMap.get(key));
            } else {
                resultMap.put(key, sourceMap.get(key));
            }
        }
        return resultMap;
    }

}
