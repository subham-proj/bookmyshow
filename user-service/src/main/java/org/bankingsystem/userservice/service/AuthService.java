package org.bankingsystem.userservice.service;

import lombok.RequiredArgsConstructor;
import org.bankingsystem.userservice.dto.AuthResponse;
import org.bankingsystem.userservice.dto.LoginRequest;
import org.bankingsystem.userservice.entity.User;
import org.bankingsystem.userservice.exception.InvalidCredentialsException;
import org.bankingsystem.userservice.repository.UserRepository;
import org.bankingsystem.userservice.security.JwtService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Value("${app.jwt.expiration-ms}")
    private long expirationMs;

    public AuthResponse login(LoginRequest request) {
        // 1. find the user by email
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password"));

        // 2. compare raw password against the stored BCrypt hash
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        // 3. issue a signed JWT
        String token = jwtService.generateToken(user.getEmail());
        return new AuthResponse(token, "Bearer", expirationMs);
    }
}
