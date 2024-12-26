package com.example.taskManagementSystem.services;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "auth-service", url = "http://auth-service:8083/api/v1/auth")
public interface AuthClientService {

    @PostMapping("/check-token")
    Boolean validateToken(@RequestHeader("Authorization") String token);

    @GetMapping("/extract-email")
    String extractEmail(@RequestHeader("Email") String email);
}
