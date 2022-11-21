package fanout;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author: LiaoMingtao
 * @date: 2022/11/15
 */
public class Consumer {

    public static void main(String[] args) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.70.111");
        factory.setPort(5672);
        factory.setUsername("admin");
        factory.setPassword("admin");
        factory.setVirtualHost("admin");
        Connection connection = null;
        Channel channel = null;
        String exchange = "lmt";
        String queue = "lmt_q";
        try {
            connection = factory.newConnection();
            channel = connection.createChannel();
            channel.exchangeDeclare(exchange, "fanout");
            channel.basicConsume(queue, true, (s, delivery) -> {
                System.out.println(delivery.getEnvelope().getDeliveryTag());
                System.out.println("收到消息:" + new String(delivery.getBody()));
            }, s -> {
            });
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }

    }
}
