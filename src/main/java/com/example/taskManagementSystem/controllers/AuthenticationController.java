//package com.example.taskManagementSystem.controllers;
//import com.example.taskManagementSystem.domain.dto.requests.UserRefreshTokenRequest;
//import com.example.taskManagementSystem.domain.dto.requests.UserSignInRequest;
//import com.example.taskManagementSystem.domain.dto.requests.UserSignUpRequest;
//import com.example.taskManagementSystem.domain.dto.responses.UserJwtAuthenticationResponse;
//import com.example.taskManagementSystem.domain.entities.TaskEntity;
//import com.example.taskManagementSystem.services.AuthenticationService;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/v1/auth")
//@RequiredArgsConstructor
//@Validated
//@Tag(name = "Авторизация и аутентификация")
//public class AuthenticationController {
//    private final AuthenticationService service;
//
//    @Operation(summary = "Зарегистрировать пользователя и получить JWT токены")
//    @PostMapping("/register")
//    public ResponseEntity<UserJwtAuthenticationResponse> register(@RequestBody @Validated UserSignUpRequest request) {
//        return new ResponseEntity<>(service.register(request), HttpStatus.CREATED);
//    }
//
//    @Operation(summary = "Аутентифицировать зарегистрированного пользователя и получить JWT токены")
//    @PostMapping("/authenticate")
//    public ResponseEntity<UserJwtAuthenticationResponse> authenticate(@RequestBody @Validated UserSignInRequest request) {
//        return ResponseEntity.ok(service.authenticate(request));
//    }
//
//    @Operation(summary = "Выпустить новую пару токенов с помощью refresh токена")
//    @PostMapping("/refresh-token")
//    public ResponseEntity<UserJwtAuthenticationResponse> refreshToken(@RequestBody @Validated UserRefreshTokenRequest request){
//        return ResponseEntity.ok(service.refreshToken(request));
//    }
//}