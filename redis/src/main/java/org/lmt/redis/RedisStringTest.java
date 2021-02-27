package org.lmt.redis;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

/**
 * @author: LiaoMingtao
 * @date: 2021/2/8
 */
@Slf4j
public class RedisStringTest {
    public static void main(String[] args) {
        String redisHost = "192.168.70.171";
        //连接本地的 Redis 服务
        Jedis jedis = new Jedis(redisHost);
        jedis.auth("admin");
        log.info("连接成功");
        //设置 redis 字符串数据
        jedis.set("lmt", "lmtTest");
        // 获取存储的数据并输出
        log.info("redis 存储的字符串为: {}", jedis.get("lmt"));
        jedis.close();
    }
}
