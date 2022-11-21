package topic;

import com.rabbitmq.client.*;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * @author: LiaoMingtao
 * @date: 2022/11/21
 */
@SuppressWarnings("all")
public class Consumer {

    public static final String VIRTUAL_HOST = "admin";
    public static final String IP = "192.168.70.109";
    public static final int PORT = 5672;
    public static final String USERNAME = "admin";
    public static final String PASSWORD = "admin";

    public static final String EXCHANGE = "zork_topic";
    public static final String QUEUE = "queue1";
    public static final String ROUTING_KEY = "#";
    public static final String OUTPUT_FILENAME = "F:\\Users\\76149\\Downloads\\rabbitmq_consumer.log";


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
            channel.basicConsume(QUEUE, true, new DeliverCallback() {
                @Override
                public void handle(String consumerTag, Delivery message) throws IOException {
                    byte[] body = message.getBody();
                    System.out.println(new String(body, StandardCharsets.UTF_8));
                    FileWriter fileWriter = new FileWriter(OUTPUT_FILENAME, true);
                    fileWriter.write(new String(body, StandardCharsets.UTF_8) + "\n");
                    fileWriter.close();
                }
            }, new CancelCallback() {
                @Override
                public void handle(String consumerTag) throws IOException {

                }
            });
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        } finally {
            // if (null != channel) {
            //     try {
            //         channel.close();
            //     } catch (IOException | TimeoutException e) {
            //         e.printStackTrace();
            //     }
            // }
            // if (null != connection) {
            //     try {
            //         connection.close();
            //     } catch (IOException e) {
            //         e.printStackTrace();
            //     }
            // }
        }
    }
}
