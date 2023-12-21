package com.javaexamples;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.ListTopicsOptions;
import org.apache.kafka.clients.admin.ListTopicsResult;

import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public class ListTopicsExample {

    public static void main(String[] args) {
        // Set up Kafka AdminClient properties
        Properties properties = new Properties();
        properties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");

        // Create Kafka AdminClient
        try (AdminClient adminClient = AdminClient.create(properties)) {
            // List topics
            Set<String> topics = listTopics(adminClient);

            // Print the list of topics
            System.out.println("List of topics in the Kafka cluster:");
            for (String topic : topics) {
                System.out.println(topic);
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static Set<String> listTopics(AdminClient adminClient) throws ExecutionException, InterruptedException {
        // Specify ListTopicsOptions if needed (e.g., to exclude internal topics)
        ListTopicsOptions options = new ListTopicsOptions();
        options.listInternal(true); // Set to false to exclude internal topics

        // List topics using AdminClient
        ListTopicsResult listTopicsResult = adminClient.listTopics(options);
        return listTopicsResult.names().get();
    }
}
