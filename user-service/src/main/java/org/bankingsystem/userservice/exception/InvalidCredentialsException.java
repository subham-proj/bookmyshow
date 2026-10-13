package org.bankingsystem.userservice.exception;

/** Thrown when login credentials don't match. Maps to HTTP 401. */
public class InvalidCredentialsException extends RuntimeException {

    public InvalidCredentialsException(String message) {
        super(message);
    }
}
