package com.taskmanagementsystem.task_management_system.service;

import com.taskmanagementsystem.task_management_system.dto.CreateUserRequest;
import com.taskmanagementsystem.task_management_system.dto.UserDTO;
import com.taskmanagementsystem.task_management_system.exception.DuplicateResourceException;
import com.taskmanagementsystem.task_management_system.exception.ResourceNotFoundException;
import com.taskmanagementsystem.task_management_system.model.User;
import com.taskmanagementsystem.task_management_system.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserDTO createUser(CreateUserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("User with email " + request.getEmail() + " already exists");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .build();

        return UserDTO.fromEntity(userRepository.save(user));
    }

    public Page<UserDTO> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(UserDTO::fromEntity);
    }

    public UserDTO getUserById(Long id) {
        return userRepository.findById(id)
                .map(UserDTO::fromEntity)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }
}
