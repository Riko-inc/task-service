package com.example.taskManagementSystem.services.impl;

import com.example.taskManagementSystem.domain.dto.requests.UserUpdateRequest;
import com.example.taskManagementSystem.domain.dto.responses.UserGetCurrentUserResponse;
import com.example.taskManagementSystem.domain.entities.UserEntity;
import com.example.taskManagementSystem.repositories.UserRepository;
import com.example.taskManagementSystem.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserEntity create(UserEntity userEntity) {
        if (userRepository.existsByEmail(userEntity.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with " + userEntity.getEmail() + " email already exists");
        }
        return userRepository.save(userEntity);
    }


    @Override
    public List<UserEntity> getAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<UserEntity> getById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<UserEntity> getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserEntity update(long userId, UserUpdateRequest userUpdateRequest) {
        return userRepository.findById(userId).map(existingUserEntity -> {
            Optional.ofNullable(userUpdateRequest.getEmail()).ifPresent(existingUserEntity::setEmail);
            Optional.ofNullable(userUpdateRequest.getPassword()).ifPresent(pwd -> existingUserEntity.setPassword(passwordEncoder.encode(pwd)));
            userRepository.save(existingUserEntity);
            return existingUserEntity;
        }).orElseThrow(() -> new RuntimeException("User does not exist!"));
    }

    @Override
    public void deleteByEmail(String email){
        userRepository.findByEmail(email).ifPresent(userRepository::delete);
    }

    public UserGetCurrentUserResponse getCurrentUser(UserEntity user) {
        return UserGetCurrentUserResponse.builder()
                .id(user.getUserId())
                .role(user.getRole())
                .email(user.getEmail())
                .registrationDateTime(user.getRegistrationDateTime()).build();
    }
}
