package org.lmt.consumer;

/**
 * @author: LiaoMingtao
 * @date: 2022/8/24
 */
public class ConstantConsumer {
    public static String BOOTSTRAP_SERVERS = "bootstrap.servers";

    /**
     * 	用来唯一标识consumer进程所在组的字符串，
     * 	如果设置同样的group  id，表示这些processes都是属于同一个consumer  group
     */
    public static String GROUP_ID = "group.id";

    /**
     * 如果值为真，则为偏移启用自动落实，否则不提交。
     */
    public static String ENABLE_AUTO_COMMIT = "enable.auto.commit";

    /**
     * consumer向zookeeper提交offset的频率，单位是秒
     */
    public static String AUTO_COMMIT_INTERVAL_MS = "auto.commit.interval.ms";

    /**
     * zookeeper 会话的超时限制。
     * 如果consumer在这段时间内没有向zookeeper发送心跳信息，则它会被认为挂掉了，并且reblance将会产生
     */
    public static String SESSION_TIMEOUT_MS = "session.timeout.ms";

    /**
     * zookeeper中没有初始化的offset时，如果offset是以下值的回应：
     * smallest：自动复位offset为smallest的offset
     * largest：自动复位offset为largest的offset
     * anything  else：向consumer抛出异常
     */
    public static String AUTO_OFFSET_RESET = "auto.offset.reset";

    public static String KEY_DESERIALIZER = "key.deserializer";

    public static String VALUE_DESERIALIZER = "value.deserializer";
}
