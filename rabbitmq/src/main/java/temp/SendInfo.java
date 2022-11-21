package temp;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeoutException;

/**
 * @author: LiaoMingtao
 * @date: 2022/11/14
 */
public class SendInfo {

    public static void main(String[] args) {
        ConnectionFactory factory = new ConnectionFactory();
        /**
         * 配置RabbitMQ的连接相关信息
         *         */
        factory.setHost("192.168.70.92");//指定IP
        factory.setPort(5672);//指定端口
        factory.setUsername("admin");//指定账号
        factory.setPassword("admin");//指定密码
        Connection connection = null;//定义连接
        Channel channel = null;//定义通道
        try {
            connection = factory.newConnection();//获取连接
            channel = connection.createChannel();//获取通道
            /**
             * 声明一个队列，
             * 参数 1 为 队列名取值任意
             * 参数 2 为 是否为持久化的队列
             * 参数 3 为是否排外 如果排外则这个队列只允许一个消费者监听
             * 参数 4 是否自动删除对了，如果为true则表示当队列中没有消息，也没有消费者链接时就会自动删除这个队列
             * 参数 5 为队列的一些属性设置通常为null即可
             * 注意：
             *    1、声明队列时，这个队列名称如果已经存在则放弃声明，如果队列不存在则会声明一个新的队列
             *    2、队列名可以取值任意，但是要与消息接收时完全一致
             *    3、这行代码是可有可无的但是一定要在发送消息前确认队列名已经存在在RabbitMQ中，否则就会出现问题             */
            channel.exchangeDeclare("myQueue", BuiltinExchangeType.FANOUT);
            // channel.queueDeclare("myQueue", true, false, false, null);
            String message = "我的RabbitMQ的测试消息222233333333344444"  + new Date();
            /**
             * 发送消息到MQ
             * 参数 1 为交换机名称 这里为空字符串表示不使用交换机
             * 参数 2 为队列名或RoutingKey，当指定了交换机名称以后这个这个值就是RoutingKey
             * 参数 3 为消息属性信息 通常空即可
             * 参数 4 为具体的消息数据的字节数组
             * 注意：队列名必须要与接收时完全一致             */
            channel.basicPublish("demo", "myQueue", null, message.getBytes("utf-8"));

            System.out.println("消息发送成功：" + message);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } finally {
            if (channel != null) {
                try {
                    channel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
