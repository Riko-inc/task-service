package com.example.taskManagementSystem.services;
import com.example.taskManagementSystem.domain.dto.responses.UserDetailResponse;
import com.example.taskManagementSystem.domain.entities.UserEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "auth-service", url = "http://auth-service:${SERVICE_PORT:8083}/api/v1/auth")
public interface AuthClientService {

    @GetMapping("/check-token")
    Boolean validateToken(@RequestHeader("Authorization") String token);

    @GetMapping("/extract-email")
    String extractEmail(@RequestHeader("Authorization") String token);

    @GetMapping("details")
    UserDetailResponse getUserDetail(@RequestHeader("Authorization") String token);

    @GetMapping("check-email")
    UserEntity checkEmail(@RequestHeader("Email") String email);
}
