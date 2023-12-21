package com.util;

import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.avro.Schema;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class KafkaProducerTest {
    public static void main(String[] args) {

        String filePath = "/Users/stephensam/projects/MP/javaexamples/src/main/resources/actual.csv";  // Update with your file path
        String topic = "kafkatopic";

//    public void testProduceMessage() {
        // Set up Kafka producer properties
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
//        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class.getName());

//
////        // Create Kafka producer
////        try (Producer<String, String> producer = new KafkaProducer<>(properties)) {
////            ProducerRecord<String, String> record = new ProducerRecord<>(topic, "key", "Yello, Kafkafromme!");
////            producer.send(record);
////        }
//
//        //Create Kafka producer to send a file content
//        try (Producer<String, String> producer = new KafkaProducer<>(properties)) {
//            // Read the file content and send it as a message
//            String fileContent = readFileContent(filePath);
//            ProducerRecord<String, String> record = new ProducerRecord<>(topic, "file-key", fileContent);
//            producer.send(record);
//
//            System.out.println("File contents sent to Kafka topic: " + topic);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


        //Send avro message to topic
        try (Producer<String, GenericRecord> producer = new KafkaProducer<>(properties)) {

            System.out.println("Avro Schema");

            String avroSchema = "{ \"type\": \"record\", \"name\": \"User\", \"fields\": [ {\"name\": \"id\", \"type\": \"int\"}, {\"name\": \"name\", \"type\": \"string\"}, {\"name\": \"age\", \"type\": \"int\"} ] }";
            Schema schema = new Schema.Parser().parse(avroSchema);

//            Schema schema = new Schema.Parser().parse(KafkaProducerTest.class.getResourceAsStream("/Users/stephensam/projects/MP/javaexamples/src/main/resources/avro_schemas/user.avsc"));

            GenericRecord userRecord = new GenericData.Record(schema);
            userRecord.put("name", "John");
            userRecord.put("age", 25);

            System.out.println(userRecord + "here");

            ProducerRecord<String, GenericRecord> record = new ProducerRecord<>(topic, "key", userRecord);
            producer.send(record);

            System.out.println("Avro message sent to Kafka topic: " + topic);

        }
    }

    private static String readFileContent(String filePath) throws IOException {
        byte[] fileBytes = Files.readAllBytes(Path.of(filePath));
        return new String(fileBytes);
    }

}
