package com.example.taskManagementSystem.controllers;

import com.example.taskManagementSystem.domain.dto.UserDto;
import com.example.taskManagementSystem.domain.dto.requests.UserUpdateRequest;
import com.example.taskManagementSystem.domain.dto.responses.UserGetCurrentUserResponse;
import com.example.taskManagementSystem.domain.dto.responses.UserUpdateResponse;
import com.example.taskManagementSystem.domain.entities.UserEntity;
import com.example.taskManagementSystem.mappers.Mapper;
import com.example.taskManagementSystem.services.UserService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
@Validated
@Tag(name = "Работа с пользователями")
public class UserController {
    private final UserService userService;
    private final Mapper<UserEntity, UserDto> userMapper;

    @Operation(summary = "Получить текущего пользователя")
    @GetMapping("/current")
    public ResponseEntity<UserGetCurrentUserResponse> getCurrentUser(@AuthenticationPrincipal UserEntity user) {
        return ResponseEntity.ok(userService.getCurrentUser(user));
    }

    @Operation(summary = "Изменить текущего пользователя (почту/пароль)")
    @PutMapping
    public ResponseEntity<UserUpdateResponse> updateUser(@AuthenticationPrincipal UserEntity user, @RequestBody @Validated UserUpdateRequest userUpdateRequest) {
        return ResponseEntity.ok(userService.update(user.getUserId(), userUpdateRequest));
    }


    @Operation(summary = "Удалить текущего пользователя")
    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteUser(@AuthenticationPrincipal UserEntity user) {
        userService.deleteByEmail(user.getEmail());
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @Operation(summary = "Получить список всех пользователей. Только для администраторов")
    @GetMapping(path = "/users")
    @PreAuthorize("hasRole('ADMIN')")
    @Hidden
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserEntity> userEntities = userService.getAll();
        return new ResponseEntity<>(userEntities.stream().map(userMapper::mapToDto).toList(), HttpStatus.OK);
    }

    @Operation(summary = "Получить информацию по пользователю. Только для администраторов")
    @GetMapping(path = "/users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Hidden
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") Long id) {
        UserEntity userEntity = userService.getById(id);
        return new ResponseEntity<>(userMapper.mapToDto(userEntity), HttpStatus.OK);

    }
}