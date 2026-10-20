package org.subhamsingh.searchservice.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.subhamsingh.searchservice.dto.EventMessage;
import org.subhamsingh.searchservice.service.EventIndexService;

@Service
@RequiredArgsConstructor
public class EventConsumer {
    private static final Logger log = LoggerFactory.getLogger(EventConsumer.class);
    private final ObjectMapper objectMapper;
    private final EventIndexService eventIndexService;
    private static final String TOPIC = "events";

    @KafkaListener(topics = TOPIC)
    public void consume(String message) {
        try{
            EventMessage event = objectMapper.readValue(message, EventMessage.class);
            eventIndexService.index(event);
            log.info("Indexed event id = {}", event.eventId());
        } catch (Exception e) {
            log.error("Failed to process event message: {}", message, e);
        }
    }
}
