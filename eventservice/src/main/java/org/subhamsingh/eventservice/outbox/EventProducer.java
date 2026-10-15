package org.subhamsingh.eventservice.outbox;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

@Service
public class EventProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final String TOPIC = "events";

    public EventProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(String key, String payload) {
        try{
            SendResult<String, String> result = kafkaTemplate.send(TOPIC, key, payload).get();

            System.out.println("Event published ::" + result.getRecordMetadata());
        } catch (Exception e) {
            throw new RuntimeException("Kafka send failed "+e);
        }
    }
}
