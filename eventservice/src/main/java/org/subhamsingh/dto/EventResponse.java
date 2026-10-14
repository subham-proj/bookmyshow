package org.subhamsingh.dto;


import org.subhamsingh.entity.Event;

import java.time.Instant;

/**
 * Outbound view of event
 * */

public record EventResponse(
        Long eventId,
        String eventName,
        Long organizerId,
        Instant startTime,
        Long duration,
        String venue,
        String eventType,
        Instant createdAt
) {

    public static EventResponse from(Event event) {
        return new EventResponse(
                event.getEventId(),
                event.getEventName(),
                event.getOrganizerId(),
                event.getStartTime(),
                event.getDuration(),
                event.getVenue(),
                event.getEventType(),
                event.getCreatedAt()
        );
    }
}
