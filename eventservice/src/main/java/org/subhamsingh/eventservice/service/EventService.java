package org.subhamsingh.eventservice.service;

import org.subhamsingh.eventservice.dto.CreateEventRequest;
import org.subhamsingh.eventservice.dto.EventResponse;

import java.util.List;

public interface EventService {

    EventResponse createEvent(CreateEventRequest req);

    EventResponse getEventById(Long eventId);

    List<EventResponse> getAllEvents();
}
