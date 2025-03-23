package com.example.taskManagementSystem.domain.dto.responses;

import com.example.taskManagementSystem.domain.entities.UserEntity;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDetailResponse {
    UserEntity.Role authorities;
    Long userId;
    String password;
    String username;
    boolean isAccountNonExpired;
    boolean isAccountNonLocked;
    boolean isCredentialsNonExpired;
    boolean isEnabled;
}
