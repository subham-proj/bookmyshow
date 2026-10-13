package org.bankingsystem.userservice.service;

import org.bankingsystem.userservice.dto.CreateUserRequest;
import org.bankingsystem.userservice.dto.UserResponse;

import java.util.List;

/**
 * Business operations for users. Kept as an interface so the web layer depends
 * on an abstraction, not a concrete implementation.
 */
public interface UserService {

    UserResponse createUser(CreateUserRequest request);

    UserResponse getUserById(Long id);

    List<UserResponse> getAllUsers();
}
