package com.example.taskManagementSystem.services;

import com.example.taskManagementSystem.domain.dto.requests.UserUpdateRequest;
import com.example.taskManagementSystem.domain.dto.responses.UserGetCurrentUserResponse;
import com.example.taskManagementSystem.domain.dto.responses.UserUpdateResponse;
import com.example.taskManagementSystem.domain.entities.UserEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    List<UserEntity> getAll();
    UserEntity getById(Long id);
    UserGetCurrentUserResponse getCurrentUser(UserEntity user);
    UserUpdateResponse update(long userId, UserUpdateRequest userUpdateRequest);
    void deleteByEmail(String email);
    UserEntity create(UserEntity userEntity);
    UserEntity getByEmail(String email);
}
