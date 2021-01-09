package org.lmt.producer;

import java.io.Closeable;
import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

/**
 * @author MSI-Gaming
 */
public class ProducerPool implements Closeable, Serializable {

    private static final long serialVersionUID = -1L;

    private Producer[] pool;

    private int threadNum = 15;
    private int size = 65535;

    /**
     * 轮循id
     */
    private int id = 0;

    private static ProducerPool instance = null;

    public static ProducerPool getInstance(Map map) {
        if (instance == null) {
            instance = new ProducerPool(map);
        }
        return ProducerPool.instance;
    }

    private ProducerPool(Map conf) {
        init(conf);
    }

    public static ProducerPool getInstance() {
        return ProducerPool.instance;
    }


    public void init(Map conf) {
        pool = new Producer[threadNum];
        for (int i = 0; i < threadNum; i++) {
            pool[i] = new Producer(conf);
        }
    }

    public Producer getProducer() {
        if (id > size) {
            id = 0;
        }
        return pool[id++ % threadNum];
    }

    @Override
    public void close() throws IOException {

    }
}
