import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author: LiaoMingtao
 * @date: 2022/11/21
 */
public class RabbitMqUtils {

    private RabbitMqUtils() {
    }

    private static final ConnectionFactory CONNECTION_FACTORY = new ConnectionFactory();
    private static Connection connection;

    static {
        //设置参数
        CONNECTION_FACTORY.setHost("192.168.70.109");
        //端口
        CONNECTION_FACTORY.setPort(5672);
        //自己在网页上创建的虚拟机
        CONNECTION_FACTORY.setVirtualHost("admin");
        //账号密码
        CONNECTION_FACTORY.setUsername("admin");
        CONNECTION_FACTORY.setPassword("admin");
    }

    /**
     * 单例双重检查
     *
     * @return rabbitmq连接
     */
    public static Connection getConnection() {
        if (null == connection) {
            synchronized (RabbitMqUtils.class) {
                if (null == connection) {
                    try {
                        connection = CONNECTION_FACTORY.newConnection();
                    } catch (IOException | TimeoutException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }

    public static void closeConnectionAndChannel(Channel channel, Connection connection) {
        try {
            if (channel != null) {
                channel.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

}
