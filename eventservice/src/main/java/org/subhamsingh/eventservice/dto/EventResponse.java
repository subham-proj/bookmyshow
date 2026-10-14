package org.subhamsingh.eventservice.dto;


import org.subhamsingh.eventservice.entity.Event;

import java.time.Instant;

/**
 * Outbound view of event.
 */
public record EventResponse(
        Long eventId,
        String eventName,
        Long organizerId,
        Instant startTime,
        Instant endTime,
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
                event.getEndTime(),
                event.getVenue(),
                event.getEventType(),
                event.getCreatedAt()
        );
    }
}
