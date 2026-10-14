package org.subhamsingh.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.Instant;

/**
 * Inbound payload for creating an event.
 *
 * Note: @NotBlank only works on String. For Long/Instant use @NotNull.
 */
public record CreateEventRequest(
        @NotBlank(message = "Event Name is required")
        String eventName,

        @NotNull(message = "Organizer id is required")
        @Positive(message = "Organizer id must be positive")
        Long organizerId,

        @NotNull(message = "Start Time is required")
        Instant startTime,

        @NotNull(message = "Event duration is required")
        @Positive(message = "Event duration must be positive")
        Long duration,

        @NotBlank(message = "Name of the venue is required")
        String venue,

        @NotBlank(message = "Type of event is required")
        String eventType
) {
}
