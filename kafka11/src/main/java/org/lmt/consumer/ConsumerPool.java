package org.lmt.consumer;

import java.io.Closeable;
import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

/**
 * @author: LiaoMingtao
 * @date: 2022/8/24
 */
public class ConsumerPool implements Closeable, Serializable {

    private static final long serialVersionUID = -1L;

    private Consumer[] pool;

    private static final int THREAD_NUM = 15;
    private static final int SIZE = 65535;

    /**
     * 轮循id
     */
    private int id = 0;

    private static ConsumerPool instance = null;

    public static ConsumerPool getInstance(Map<String, Object> map) {
        if (instance == null) {
            instance = new ConsumerPool(map);
        }
        return ConsumerPool.instance;
    }

    private ConsumerPool(Map<String, Object> conf) {
        init(conf);
    }

    public static ConsumerPool getInstance() {
        return ConsumerPool.instance;
    }


    public void init(Map<String, Object> conf) {
        pool = new Consumer[THREAD_NUM];
        for (int i = 0; i < THREAD_NUM; i++) {
            pool[i] = new Consumer(conf);
        }
    }

    public Consumer getConsumer() {
        if (id > SIZE) {
            id = 0;
        }
        return pool[id++ % THREAD_NUM];
    }

    @Override
    public void close() throws IOException {

    }
}
