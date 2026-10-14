package org.subhamsingh.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.Instant;

/**
 * Inbound payload for creating an event
 * */
public record CreateEventRequest(
        @NotBlank(message = "Event Name is required")
        String eventName,

        @NotBlank(message = "Organizer id is required")
        Long organizerId,

        @NotBlank(message = "Start Time is required")
        Instant startTime,

        @NotBlank(message = "Event duration is required")
        Long duration,

        @NotBlank(message = "Name of the venue is required")
        String venue,

        @NotBlank(message = "Type of event is required")
        String eventType
) {
}
