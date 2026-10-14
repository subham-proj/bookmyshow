package org.subhamsingh.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.subhamsingh.dto.CreateEventRequest;
import org.subhamsingh.dto.EventResponse;
import org.subhamsingh.entity.Event;
import org.subhamsingh.repository.EventRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;

    @Override
    @Transactional
    public EventResponse createEvent(CreateEventRequest req) {
        Event event = Event.builder()
                .eventName(req.eventName())
                .organizerId(req.organizerId())
                .startTime(req.startTime())
                .duration(req.duration())
                .venue(req.venue())
                .eventType(req.eventType())
                .build();

        return EventResponse.from(eventRepository.save(event));
    }

    @Override
    @Transactional(readOnly = true)
    public EventResponse getEventById(Long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow();
        return EventResponse.from(event);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventResponse> getAllEvents() {
        return eventRepository.findAll().stream().map(EventResponse::from).toList();
    }
}
