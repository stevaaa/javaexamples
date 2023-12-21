package com.javaexamples;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class CustomClientIdConsumer {

    public static void main(String[] args) {
        // Set your Kafka bootstrap servers
        String bootstrapServers = "localhost:9092";

        // Set your consumer group and custom client ID
        String groupId = "your-consumer-group";
        String customClientId = "your-custom-client-id";

        // Set Kafka consumer properties
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);

        // Set the custom client ID
        properties.put(ConsumerConfig.CLIENT_ID_CONFIG, customClientId);

        // Set key and value deserializers
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");

        // Create Kafka consumer
        Consumer<String, String> consumer = new KafkaConsumer<>(properties);

        // Subscribe to the topic(s)
        consumer.subscribe(Collections.singletonList("kafkatopic"));

        // Start the poll loop
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));

            // Process the records...
        }
    }
}
