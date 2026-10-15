package org.subhamsingh.eventservice.outbox;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.subhamsingh.eventservice.entity.Event;
import org.subhamsingh.eventservice.repository.OutboxRepository;

@Component
@RequiredArgsConstructor
public class OutboxWriter {

    private final OutboxRepository outboxRepository;
    private final ObjectMapper objectMapper;

    public void addToOutbox(Event event) {
        Outbox outboxRow = Outbox.builder()
                .aggregateId(event.getEventId())
                .payload(toJson(event))
                .build();

        outboxRepository.save(outboxRow);
    }

    private String toJson(Event event) {
        try {
            return objectMapper.writeValueAsString(event);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize event to JSON", e);
        }
    }
}
