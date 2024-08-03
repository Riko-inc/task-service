package com.example.taskManagementSystem.services;

import com.example.taskManagementSystem.domain.dto.requests.UserUpdateRequest;
import com.example.taskManagementSystem.domain.dto.responses.UserGetCurrentUserResponse;
import com.example.taskManagementSystem.domain.entities.UserEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {
    List<UserEntity> getAll();
    Optional<UserEntity> getById(Long id);
    UserGetCurrentUserResponse getCurrentUser(UserEntity user);
    UserEntity update(long userId, UserUpdateRequest userUpdateRequest);
    void deleteByEmail(String email);
    UserEntity create(UserEntity userEntity);
    Optional<UserEntity> getByEmail(String email);
}
