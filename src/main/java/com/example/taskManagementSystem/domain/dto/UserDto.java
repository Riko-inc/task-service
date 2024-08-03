package com.example.taskManagementSystem.domain.dto;
import com.example.taskManagementSystem.domain.entities.UserEntity;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private Long userId;
    private String email;
    private String password;
    private UserEntity.Role role;
    private LocalDateTime registrationDateTime;
    private boolean isActive;
}