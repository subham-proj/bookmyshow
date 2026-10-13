package org.bankingsystem.userservice.dto;

/** Returned on successful login. The client sends this token back as
 *  "Authorization: Bearer <token>" on protected requests. */
public record AuthResponse(
        String token,
        String tokenType,
        long expiresInMs
) {
}
