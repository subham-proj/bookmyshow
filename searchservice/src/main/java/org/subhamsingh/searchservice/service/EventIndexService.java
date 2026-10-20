package org.subhamsingh.searchservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.subhamsingh.searchservice.document.EventDocument;
import org.subhamsingh.searchservice.dto.EventMessage;
import org.subhamsingh.searchservice.repository.EventSearchRepository;

@Service
@RequiredArgsConstructor
public class EventIndexService {

    private final EventSearchRepository eventSearchRepository;

    public void index(EventMessage msg) {
        EventDocument doc = EventDocument.builder()
                .eventId(msg.eventId())
                .eventName(msg.eventName())
                .organizerId(msg.organizerId())
                .startTime(msg.startTime())
                .endTime(msg.endTime())
                .venue(msg.venue())
                .eventType(msg.eventType())
                .createdAt(msg.createdAt())
                .updatedAt(msg.updatedAt())
                .build();

        // save() is an upsert keyed on _id (eventId) -> idempotent on replays.
        eventSearchRepository.save(doc);
    }
}
