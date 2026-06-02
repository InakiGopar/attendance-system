package com.backend.attendancesystem.user.service;

import com.backend.attendancesystem.user.dto.UserRequest;
import com.backend.attendancesystem.user.dto.UserResponse;
import com.backend.attendancesystem.user.mapper.UserMapper;
import com.backend.attendancesystem.user.model.UserEntity;
import com.backend.attendancesystem.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public UserResponse saveUser(UserRequest request) {
        return UserMapper.toResponse(
                userRepository.save(
                        UserMapper.toEntity(request)
                ));
    }

    @Transactional
    public UserResponse updateUser(UUID userId, UserRequest request) {
        //todo: add exception handling
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setRole(request.role());
        user.setName(request.name());
        user.setLastName(request.lastName());
        user.setEmail(request.email());
        user.setPassword(request.password());

        return UserMapper.toResponse(user);
    }

    @Transactional
    public void deleteUser(UUID userId) {
        //todo: add exception handling
        userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.deleteById(userId);
    }

    public UserResponse getUser(UUID userId) {
        //todo: add exception handling
         return UserMapper.toResponse(
                 userRepository.findById(userId)
                         .orElseThrow(() -> new RuntimeException("User not found"))
         );

    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserMapper::toResponse)
                .toList();
    }
}
