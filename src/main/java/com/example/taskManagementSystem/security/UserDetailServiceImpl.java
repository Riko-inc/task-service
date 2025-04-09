package com.example.taskManagementSystem.security;

import com.example.taskManagementSystem.domain.dto.responses.UserDetailResponse;
import com.example.taskManagementSystem.domain.entities.UserEntity;
import com.example.taskManagementSystem.services.AuthClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 * //this class is used by spring controller to authenticate and authorize user
 * modified this class to user our Database and defined user roles
 *
 */
@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {
    private final AuthClientService authClientService;

    @Override
    public UserDetails loadUserByUsername(String token) {
        UserDetailResponse response = authClientService.getUserDetail();
        return UserEntity
                .builder()
                .role(response.getAuthorities())
                .userId(response.getUserId())
                .email(response.getUsername())
                .password(response.getPassword())
                .isActive(response.isEnabled())
                .build();
    }
}

//
//    private UserEntity buildUserFromUserEntity(UserEntity userEntity) {
//        // convert model user to spring security user
//        Long id = userEntity.getUserId();
//        String password = userEntity.getPassword();
//        boolean enabled = true;
//        boolean accountNonExpired = true;
//        boolean credentialsNonExpired = true;
//        boolean accountNonLocked = true;
//        GrantedAuthority[] authorities = new GrantedAuthorityImpl[1];
//        authorities[0] = new GrantedAuthorityImpl(userEntity.getRole());
//
//        UserEntity springUser = new UserEntity(username, password, enabled,
//                accountNonExpired, credentialsNonExpired, accountNonLocked,
//                authorities);
//        return springUser;
//    }
//
//}