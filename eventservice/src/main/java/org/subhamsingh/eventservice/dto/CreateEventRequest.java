package org.subhamsingh.eventservice.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.subhamsingh.eventservice.dto.validation.EndAfterStart;

import java.time.Instant;

/**
 * Inbound payload for creating an event.
 *
 * Note: @NotBlank only works on String. For Long/Instant use @NotNull.
 * @EndAfterStart is a class-level constraint that compares two fields
 * (endTime must be strictly after startTime).
 */
@EndAfterStart
public record CreateEventRequest(
        @NotBlank(message = "Event Name is required")
        String eventName,

        @NotNull(message = "Organizer id is required")
        @Positive(message = "Organizer id must be positive")
        Long organizerId,

        @NotNull(message = "Start Time is required")
        @Future(message = "Start time must be in the future")
        Instant startTime,

        @NotNull(message = "End Time is required")
        Instant endTime,

        @NotBlank(message = "Name of the venue is required")
        String venue,

        @NotBlank(message = "Type of event is required")
        String eventType
) {
}
