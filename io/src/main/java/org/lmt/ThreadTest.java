package org.lmt;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

/**
 * @author：hubinbin
 * @date：2021/8/30
 */
public class ThreadTest {
    private static ExecutorService threadPoolExecutor = Executors.newFixedThreadPool(17);

    public static void main(String[] args) {
        long start, end;
        start = System.currentTimeMillis();
        File file = new File("E:\\test.log");
        //File file = new File("C:/Users/72900/Desktop/接口/调用链/dongfang_20191014.txt");
        try (
                FileInputStream fis = new FileInputStream(file);
                InputStreamReader isw = new InputStreamReader(fis);
                BufferedReader br = new BufferedReader(isw);) {
            StringBuffer buffer = new StringBuffer();
            String line;
            List<Future<Map<String, Integer>>> futureList = new ArrayList<>();
            int i = 0;
            while ((line = br.readLine()) != null) {
//                line.length() > 2 && line.substring(line.length()-2, line.length() ).equals("\\n") &&
                if (i == 10000) {
                    i = 0;
//                    list.add(buffer.toString());
                    Future<Map<String, Integer>> future = threadPoolExecutor.submit(new WordThread(buffer.toString()));
                    futureList.add(future);
                    buffer = new StringBuffer();
                    buffer.append(line + "\n");
                } else {
                    buffer.append(line + "\n");
                }
                i++;
            }

            if (line == null) {
                Future<Map<String, Integer>> future = threadPoolExecutor.submit(new WordThread(buffer.toString()));
                futureList.add(future);
            }

//            for (String lines : list) {
//                Future<Map<String, Integer>> future = threadPoolExecutor.submit(new WordThread(lines));
//                futureList.add(future);
//            }
            Map<String, Integer> mergeMap = new HashMap<>(1024);
            for (Future<Map<String, Integer>> future : futureList) {
                mergeMap = mergeMap(mergeMap, future.get());
            }
            List<String> wordTop = sortMapByValue(sortDescend(mergeMap), 10);
            wordTop.stream().forEach(x -> System.out.println(x));
            end = System.currentTimeMillis();
            System.out.println("start time:" + start + "; end time:" + end + "; Run Time:" + (end - start) + "(ms)");
            threadPoolExecutor.shutdown();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static List<String> sortMapByValue(Map<String, Integer> map, Integer num) {
        Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        List<String> wordList = new ArrayList<>(10);
        List<Map.Entry<String, Integer>> lists = new ArrayList<Map.Entry<String, Integer>>(map.entrySet());
        Collections.sort(lists, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                Integer q1 = o1.getValue();
                Integer q2 = o2.getValue();
                Integer p = q2 - q1;
                if (p > 0) {
                    return 1;
                } else if (p == 0) {
                    return 0;
                } else {
                    return -1;
                }
            }
        });

        if (lists.size() >= num) {

            //lists.subList()用法
            for (Map.Entry<String, Integer> set : lists.subList(0, num)) {
                wordList.add(set.getKey());
            }
        } else {
            for (Map.Entry<String, Integer> set : lists) {
                wordList.add(set.getKey());
            }
        }
        return wordList;

    }

    // Map的value值降序排序
    public static <K, V extends Comparable<? super V>> Map<K, V> sortDescend(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
            @Override
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                int compare = (o1.getValue()).compareTo(o2.getValue());
                return -compare;
            }
        });

        Map<K, V> returnMap = new LinkedHashMap<K, V>();
        for (Map.Entry<K, V> entry : list) {
            returnMap.put(entry.getKey(), entry.getValue());
        }
        return returnMap;
    }

    public static Map<String, Integer> mergeMap(Map<String, Integer> a, Map<String, Integer> b) {
        Object[] os = b.keySet().toArray();
        String key;
        for (int i = 0; i < os.length; i++) {
            key = (String) os[i];
            if (a.containsKey(key))
                a.put(key, a.get(key) + b.get(key));
            else
                a.put(key, b.get(key));
        }
        return a;
    }


}
