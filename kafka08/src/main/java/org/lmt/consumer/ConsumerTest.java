package org.lmt.consumer;


import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;
import kafka.serializer.Decoder;
import kafka.utils.VerifiableProperties;
import org.apache.avro.generic.GenericRecord;
import org.lmt.avro.AvroDeserializerFactory;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Thinkpad
 */
public class ConsumerTest {

    private final ConsumerConnector consumer;


    public ConsumerTest() {
        Properties props = new Properties();
        props.put("zookeeper.connect", "kafka-1:2181/kafka082,kafka-2:2181/kafka082,kafka-3:2181/kafka082");
        props.put("group.id", UUID.randomUUID().toString());
        props.put("zookeeper.session.timeout.ms", "10000");
        props.put("zookeeper.sync.time.ms", "200");
        props.put("auto.commit.interval.ms", "1000");
        props.put("auto.offset.reset", "smallest");
        // props.put("serializer.class", "kafka.serializer.StringEncoder");
        // props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        // props.put("value.deserializer", "org.apache.kafka.common.serialization.ByteArrayDeserializer");

        consumer = Consumer.createJavaConsumerConnector(new ConsumerConfig(props));
    }

    public void consume() {
        Map<String, Integer> topicCountMap = new HashMap<>(8);
        topicCountMap.put("dys_success87", new Integer(1));
        Decoder<String> keyDecoder = new kafka.serializer.StringDecoder(new VerifiableProperties());
        Decoder<byte[]> valueDecoder = new kafka.serializer.DefaultDecoder(new VerifiableProperties());

        Map<String, List<KafkaStream<String, byte[]>>> map = consumer.createMessageStreams(topicCountMap, keyDecoder, valueDecoder);
        List<KafkaStream<String, byte[]>> kafkaStreams = map.get("dys_success87");
        ExecutorService executor = Executors.newFixedThreadPool(1);

        for (final KafkaStream<String, byte[]> kafkaStream : kafkaStreams) {
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    ConsumerIterator<String, byte[]> iterator = kafkaStream.iterator();
                    while (iterator.hasNext()) {
                        MessageAndMetadata<String, byte[]> messageAndMetadata = iterator.next();
                        GenericRecord record = AvroDeserializerFactory.getLogsDeserializer()
                                .deserialize(messageAndMetadata.message());
                        System.out.println(record);
                        //System.out.println("message : " + messageAndMetadata.message() + "  partition :  " + messageAndMetadata.partition());
                    }
                }
            });


        }
    }

    public static void main(String[] args) {
        new ConsumerTest().consume();
    }
}
