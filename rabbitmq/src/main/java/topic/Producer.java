package topic;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.concurrent.TimeoutException;

/**
 * @author: LiaoMingtao
 * @date: 2022/11/21
 */
@SuppressWarnings("all")
public class Producer {

    public static final String VIRTUAL_HOST = "admin";
    public static final String IP = "192.168.70.109";
    public static final int PORT = 5672;
    public static final String USERNAME = "admin";
    public static final String PASSWORD = "admin";

    public static final String EXCHANGE = "zork_topic";
    public static final String QUEUE = "";
    public static final String ROUTING_KEY = "#";


    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setVirtualHost(VIRTUAL_HOST);
        connectionFactory.setHost(IP);
        connectionFactory.setPort(PORT);
        connectionFactory.setUsername(USERNAME);
        connectionFactory.setPassword(PASSWORD);
        Connection connection = null;
        Channel channel = null;
        try {
            connection = connectionFactory.newConnection();
            channel = connection.createChannel();
            channel.exchangeDeclare(EXCHANGE, BuiltinExchangeType.TOPIC, true);
            channel.basicPublish(EXCHANGE, ROUTING_KEY, null, (new Date() + "topic模式").getBytes(StandardCharsets.UTF_8));
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        } finally {
            if (null != channel) {
                try {
                    channel.close();
                } catch (IOException | TimeoutException e) {
                    e.printStackTrace();
                }
            }
            if (null != connection) {
                try {
                    connection.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
