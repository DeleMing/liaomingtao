package org.lmt.producer;

import java.io.Closeable;
import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

/**
 * @author Thinkpad
 */
public class ProducerPool implements Closeable, Serializable {

    private static final long serialVersionUID = -1L;

    private Producer[] pool;

    private static final int THREAD_NUM = 15;
    private static final int SIZE = 65535;

    /**
     * 轮循id
     */
    private int id = 0;

    private static ProducerPool instance = null;

    public static ProducerPool getInstance(Map<String, Object> map) {
        if (instance == null) {
            instance = new ProducerPool(map);
        }
        return ProducerPool.instance;
    }

    private ProducerPool(Map<String, Object> conf) {
        init(conf);
    }

    public static ProducerPool getInstance() {
        return ProducerPool.instance;
    }


    public void init(Map<String, Object> conf) {
        pool = new Producer[THREAD_NUM];
        for (int i = 0; i < THREAD_NUM; i++) {
            pool[i] = new Producer(conf);
        }
    }

    public Producer getProducer() {
        if (id > SIZE) {
            id = 0;
        }
        return pool[id++ % THREAD_NUM];
    }

    @Override
    public void close() throws IOException {

    }
}
