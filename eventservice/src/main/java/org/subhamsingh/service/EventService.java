package org.subhamsingh.service;

import org.subhamsingh.dto.CreateEventRequest;
import org.subhamsingh.dto.EventResponse;

import java.util.List;

public interface EventService {

    EventResponse createEvent(CreateEventRequest req);

    EventResponse getEventById(Long eventId);

    List<EventResponse> getAllEvents();
}
