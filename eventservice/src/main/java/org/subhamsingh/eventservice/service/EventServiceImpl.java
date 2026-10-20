package org.subhamsingh.eventservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
    @Transactional
    @CacheEvict(cacheNames = "events", key = "#eventId")   // drop stale cache entry
    public EventResponse updateEvent(Long eventId, CreateEventRequest req) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + eventId));

        event.setEventName(req.eventName());
        event.setOrganizerId(req.organizerId());
        event.setStartTime(req.startTime());
        event.setEndTime(req.endTime());
        event.setVenue(req.venue());
        event.setEventType(req.eventType());

        EventResponse response = EventResponse.from(eventRepository.save(event));
        // re-publish so the search index (ES) reflects the update too
        outboxWriter.addToOutbox(event);
        return response;
    }

    // Cached in Redis under key "events::{eventId}" for 1h; evicted on update above.
    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "events", key = "#eventId")
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
