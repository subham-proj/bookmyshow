package org.subhamsingh.userservice.dto;

import org.subhamsingh.userservice.entity.User;

import java.time.Instant;

/**
 * Outbound view of a user. Note it deliberately excludes the password so it
 * never leaks over the API.
 */
public record UserResponse(
        Long id,
        String name,
        String email,
        String phoneNumber,
        Instant createdAt
) {

    public static UserResponse from(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getCreatedAt()
        );
    }
}
