package org.subhamsingh.searchservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.Instant;

/**
 * Contract for messages consumed from the "events" Kafka topic.
 * ignoreUnknown = true keeps us resilient: the producer can add fields
 * without breaking this consumer.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record EventMessage(
        Long eventId,
        String eventName,
        Long organizerId,
        Instant startTime,
        Instant endTime,
        String venue,
        String eventType,
        Instant createdAt,
        Instant updatedAt
) {
}
