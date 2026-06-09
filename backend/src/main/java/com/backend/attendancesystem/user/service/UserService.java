package com.backend.attendancesystem.user.service;

import com.backend.attendancesystem.institution.repository.InstitutionRepository;
import com.backend.attendancesystem.user.dto.UserRequest;
import com.backend.attendancesystem.user.dto.UserResponse;
import com.backend.attendancesystem.user.mapper.UserMapper;
import com.backend.attendancesystem.user.model.UserEntity;
import com.backend.attendancesystem.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final InstitutionRepository institutionRepository;

    @Transactional
    public UserResponse saveUser(UserRequest request) {
        //check request
        validateInstitutionExists(request.institutionId());

        return UserMapper.toResponse(
                userRepository.save(
                        UserMapper.toEntity(request)
                ));
    }

    @Transactional
    public UserResponse updateUser(UUID userId, UserRequest request) {
        //check 1
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        //check 2
        validateInstitutionExists(request.institutionId());

        user.setRole(request.role());
        user.setName(request.name());
        user.setLastName(request.lastName());
        user.setEmail(request.email());
        user.setPassword(request.password());

        return UserMapper.toResponse(user);
    }

    @Transactional
    public void deleteUser(UUID userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId ));
        userRepository.deleteById(userId);
    }

    public UserResponse getUser(UUID userId) {
         return UserMapper.toResponse(
                 userRepository.findById(userId)
                         .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId))
         );

    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserMapper::toResponse)
                .toList();
    }

    //helper method
    private void validateInstitutionExists(UUID institutionId) {
        institutionRepository.findById(institutionId)
                .orElseThrow(() -> new EntityNotFoundException("Institution not found with id: " + institutionId));
    }
}
