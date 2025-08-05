package com.example.taskManagementSystem.services;
import com.example.taskManagementSystem.config.FeignConfig;
import com.example.taskManagementSystem.domain.dto.responses.UserDetailResponse;
import com.example.taskManagementSystem.domain.entities.UserEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "space-service", url = "http://space-service:${SERVICE_PORT:8085}/api/v1/", configuration = FeignConfig.class)
public interface SpaceClientService {

//    @GetMapping("/auth/check-token")
//    Boolean validateToken();
//
//    @GetMapping("/auth/details")
//    UserDetailResponse getUserDetail();
//
//    @GetMapping("/user/extract-email")
//    String extractEmail();
//
//    @GetMapping("/user/check-email")
//    UserEntity checkEmail(@RequestHeader("Email") String email);
//
//    @GetMapping("/user/check-id")
//    Boolean checkUserIdExists(@RequestHeader("Id") Long userId);
}
