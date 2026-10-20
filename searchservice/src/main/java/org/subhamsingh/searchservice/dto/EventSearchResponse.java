package org.subhamsingh.searchservice.dto;

import org.subhamsingh.searchservice.document.EventDocument;

import java.time.Instant;

/** Search-result view of an event (subset of the stored document). */
public record EventSearchResponse(
        Long eventId,
        String eventName,
        String venue,
        String eventType,
        Instant startTime,
        Instant endTime
) {
    public static EventSearchResponse from(EventDocument doc) {
        return new EventSearchResponse(
                doc.getEventId(),
                doc.getEventName(),
                doc.getVenue(),
                doc.getEventType(),
                doc.getStartTime(),
                doc.getEndTime()
        );
    }
}
