package org.lmt;

import java.io.File;
import java.util.*;
import java.util.concurrent.*;

/**
 * @author: LiaoMingtao
 * @date: 2021/8/30
 */
public class WordFrequencyCount {

    public static void wordFrequencyCount(int n) throws ExecutionException, InterruptedException {
        File wf = new File("E:\\test1.log");
        int taskNum = n;
        // 创建一个线程池
        ExecutorService pool = Executors.newFixedThreadPool(taskNum);
        // 创建多个有返回值的任务
        List<Future> list = new ArrayList<Future>();
        for (int i = 0; i < taskNum; i++) {
            Callable c = new MyCallable(wf, i/taskNum, (i+1)/taskNum*wf.length());
            // 执行任务并获取Future对象
            Future f = pool.submit(c);
            list.add(f);
        }
        // 关闭线程池
        pool.shutdown();
        // 获取所有并发任务的运行结果
        TreeMap tMap = new TreeMap();
        for (Future f : list) {
            HashMap hMap = (HashMap) f.get();
            //对不同线程处理的结果进行整合
            tMap.putAll(hMap);
        }

        //打印输出，查看结果
        //Iterator iterator = tMap.entrySet().iterator();
        List<Map.Entry<String, Integer>> entrieList = new ArrayList<Map.Entry<String, Integer>>(tMap.entrySet());
        //通过比较器实现排序
        Collections.sort(entrieList, new Comparator<Map.Entry<String, Integer>>() {
            //降序排序
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        for (int i = 0; i < 10; i++){
            Map.Entry<String, Integer> map = entrieList.remove(i);
            System.out.println(map.getKey() + ":" + map.getValue());
        }

    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        wordFrequencyCount(10);
    }
}
