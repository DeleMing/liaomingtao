package org.lmt.redis;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

/**
 * @author: LiaoMingtao
 * @date: 2021/2/8
 */
@Slf4j
public class RedisConnectTest {
    public static void main(String[] args) {
        String redisHost = "192.168.70.171";
        //连接本地的 Redis 服务
        Jedis jedis = new Jedis(redisHost);
        // 如果 Redis 服务设置来密码，需要下面这行，没有就不需要
        jedis.auth("admin");
        log.info("连接成功");
        //查看服务是否运行
        log.info("服务正在运行：{}", jedis.ping());
        jedis.close();
    }
}
