package topic;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * @author: LiaoMingtao
 * @date: 2022/11/21
 */
@SuppressWarnings("all")
public class ProducerTest2 {

    public static final String VIRTUAL_HOST = "admin";
    public static final String IP = "192.168.3.21";
    public static final int PORT = 5672;
    public static final String USERNAME = "admin";
    public static final String PASSWORD = "admin";

    public static final String EXCHANGE = "topic.zorkdata.exchange";
    public static final String QUEUE = "topic.zorkdata.queue";
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
            BufferedReader bufferedReader = null;
            try {
                bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream("E:\\tmp\\金证监控对接\\rabbitmq_consumer_1\\rabbitmq_consumer.log"), "GBK"));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    // Thread.sleep(200L);
                    channel.basicPublish(EXCHANGE, ROUTING_KEY, null, line.getBytes(StandardCharsets.UTF_8));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


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
