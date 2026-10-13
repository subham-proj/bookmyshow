package org.bankingsystem.userservice.service;

import lombok.RequiredArgsConstructor;
import org.bankingsystem.userservice.dto.CreateUserRequest;
import org.bankingsystem.userservice.dto.UserResponse;
import org.bankingsystem.userservice.entity.User;
import org.bankingsystem.userservice.exception.DuplicateResourceException;
import org.bankingsystem.userservice.exception.ResourceNotFoundException;
import org.bankingsystem.userservice.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    // Injected by Spring via the constructor that @RequiredArgsConstructor
    // generates. The actual object is the BCryptPasswordEncoder bean from
    // SecurityConfig.passwordEncoder().
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserResponse createUser(CreateUserRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new DuplicateResourceException("A user with email '" + request.email() + "' already exists");
        }
        if (request.phoneNumber() != null && userRepository.existsByPhoneNumber(request.phoneNumber())) {
            throw new DuplicateResourceException("A user with phone number '" + request.phoneNumber() + "' already exists");
        }

        User user = User.builder()
                .name(request.name())
                .email(request.email())
                .phoneNumber(request.phoneNumber())
                // Store the BCrypt hash, never the raw password.
                .password(passwordEncoder.encode(request.password()))
                .build();

        return UserResponse.from(userRepository.save(user));
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return UserResponse.from(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserResponse::from)
                .toList();
    }
}
