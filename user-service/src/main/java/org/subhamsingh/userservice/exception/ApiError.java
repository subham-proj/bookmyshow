package org.subhamsingh.userservice.exception;

import java.time.Instant;
import java.util.Map;

/**
 * Consistent error body returned to clients.
 *
 * @param fieldErrors per-field validation messages; null for non-validation errors.
 */
public record ApiError(
        Instant timestamp,
        int status,
        String error,
        String message,
        String path,
        Map<String, String> fieldErrors
) {
    public static ApiError of(int status, String error, String message, String path) {
        return new ApiError(Instant.now(), status, error, message, path, null);
    }

    public static ApiError of(int status, String error, String message, String path, Map<String, String> fieldErrors) {
        return new ApiError(Instant.now(), status, error, message, path, fieldErrors);
    }
}
