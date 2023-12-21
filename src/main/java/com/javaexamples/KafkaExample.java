package com.javaexamples;

import com.util.KafkaUtil;

public class KafkaExample {
    private static KafkaUtil kafkaUtil;

    public static void main(String[] args) {

        String kafkaServer = "localhost:9092";
        String groupID = "default-group";
        kafkaUtil = new KafkaUtil(kafkaServer, groupID);

        kafkaUtil.subscribeToTopic("kafkatopic");
        String message = kafkaUtil.consumeMessage();

        System.out.println(message);

        kafkaUtil.close();

    }

}
