package com.util;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class KafkaUtil {

    private final Consumer<String, String> kafkaConsumer;

    public KafkaUtil(String bootstrapServers, String groupId) {
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        this.kafkaConsumer = new KafkaConsumer<>(properties);
    }

    public void subscribeToTopic(String topic) {
        kafkaConsumer.subscribe(Collections.singletonList(topic));
    }

    public String consumeMessage() {
        ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofMillis(1000));

        // Assuming you're only interested in the first message received
        if (!records.isEmpty()) {
            return records.iterator().next().value();
        } else {
            return null; // No message received within the specified timeout
        }
    }

    public void close() {
        kafkaConsumer.close();
    }

}
