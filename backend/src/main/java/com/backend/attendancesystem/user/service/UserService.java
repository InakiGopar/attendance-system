package com.backend.attendancesystem.user.service;

import com.backend.attendancesystem.auth.dto.GoogleUserInfo;
import com.backend.attendancesystem.enums.RoleType;
import com.backend.attendancesystem.enums.WeekDay;
import com.backend.attendancesystem.enums.mapper.WeekDayMapper;
import com.backend.attendancesystem.institution.repository.InstitutionRepository;
import com.backend.attendancesystem.schedule.repository.ScheduleRepository;
import com.backend.attendancesystem.user.dto.response.CurrentUserResponse;
import com.backend.attendancesystem.user.dto.response.UserCourseResponse;
import com.backend.attendancesystem.user.dto.request.UserRequest;
import com.backend.attendancesystem.user.dto.response.UserResponse;
import com.backend.attendancesystem.user.mapper.UserMapper;
import com.backend.attendancesystem.user.model.UserEntity;
import com.backend.attendancesystem.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final InstitutionRepository institutionRepository;
    private final ScheduleRepository scheduleRepository;

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
    public void registerOAuth2User(GoogleUserInfo userInfo, String institutionId) {

        System.out.println("llego al servicio! " + institutionId);

        UserEntity user = new UserEntity();

        user.setUserId(UUID.randomUUID());
        user.setEmail(userInfo.email());
        user.setName(userInfo.name());
        user.setLastName(userInfo.lastName());

        user.setRole(RoleType.TEACHER);

        userRepository.save(user);

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

    public CurrentUserResponse getCurrentUserInfo(String email) {

        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "User not found with email: " + email
                        ));

        return new CurrentUserResponse(
                user.getUserId(),
                user.getName(),
                user.getLastName(),
                user.getEmail(),
                user.getInstitution().getInstitutionId(),
                user.getRole()
        );
    }


    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserMapper::toResponse)
                .toList();
    }

    public List<UserCourseResponse> getTodayCourses(UUID userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        WeekDay today = WeekDayMapper.convertDayOfWeek(LocalDate.now().getDayOfWeek());

        return scheduleRepository.findCoursesByUserAndWeekDay(userId, today);

    }

    public Optional<UserEntity> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    //helper method
    private void validateInstitutionExists(UUID institutionId) {
        institutionRepository.findById(institutionId)
                .orElseThrow(() -> new EntityNotFoundException("Institution not found with id: " + institutionId));
    }
}
