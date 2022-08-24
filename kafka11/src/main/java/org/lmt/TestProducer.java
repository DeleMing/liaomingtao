package org.lmt;

import org.lmt.producer.KafkaConstants;
import org.lmt.producer.ProducerPool;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: LiaoMingtao
 * @date: 2022/8/24
 */
public class TestProducer {

    public static void main(String[] args) {
        Map<String, Object> map = new HashMap<>(8);
        map.put(KafkaConstants.BOOTSTRAP_SERVERS_CONFIG, "192.168.70.109:9092,192.168.70.110:9092,192.168.70.111:9092");
        ProducerPool producerPool = ProducerPool.getInstance(map);
        for (int i = 0; i < 1000; i++) {
            producerPool.getProducer().sendStr("lmt", i + 1 + "");
        }
    }
}
