package org.subhamsingh.eventservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.subhamsingh.eventservice.dto.CreateEventRequest;
import org.subhamsingh.eventservice.dto.EventResponse;
import org.subhamsingh.eventservice.entity.Event;
import org.subhamsingh.eventservice.exception.ResourceNotFoundException;
import org.subhamsingh.eventservice.outbox.OutboxWriter;
import org.subhamsingh.eventservice.repository.EventRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final OutboxWriter outboxWriter;

    @Override
    @Transactional
    public EventResponse createEvent(CreateEventRequest req) {
        Event event = Event.builder()
                .eventName(req.eventName())
                .organizerId(req.organizerId())
                .startTime(req.startTime())
                .endTime(req.endTime())
                .venue(req.venue())
                .eventType(req.eventType())
                .build();

        EventResponse response = EventResponse.from(eventRepository.save(event));
        outboxWriter.addToOutbox(event);
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public EventResponse getEventById(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + eventId));
        return EventResponse.from(event);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventResponse> getAllEvents() {
        return eventRepository.findAll().stream().map(EventResponse::from).toList();
    }
}
