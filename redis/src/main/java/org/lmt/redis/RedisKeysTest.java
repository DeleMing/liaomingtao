package org.lmt.redis;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @author: LiaoMingtao
 * @date: 2021/2/8
 */
@Slf4j
public class RedisKeysTest {
    public static void main(String[] args) {
        String redisHost = "192.168.70.171";
        //连接本地的 Redis 服务
        Jedis jedis = new Jedis(redisHost);
        jedis.auth("admin");
        log.info("连接成功");
        //存储数据到列表中
        // 获取数据并输出
        Set<String> keys = jedis.keys("*");
        Iterator<String> it = keys.iterator();
        while (it.hasNext()) {
            String key = it.next();
            log.info("key:{}", key);
        }
        jedis.close();
    }
}
