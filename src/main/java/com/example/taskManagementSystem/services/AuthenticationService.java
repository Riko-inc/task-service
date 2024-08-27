package com.example.taskManagementSystem.services;
import com.example.taskManagementSystem.domain.dto.requests.UserRefreshTokenRequest;
import com.example.taskManagementSystem.domain.dto.requests.UserSignInRequest;
import com.example.taskManagementSystem.domain.dto.requests.UserSignUpRequest;
import com.example.taskManagementSystem.domain.dto.responses.UserJwtAuthenticationResponse;
import com.example.taskManagementSystem.domain.entities.UserEntity;
import com.example.taskManagementSystem.exceptions.AccessDeniedException;
import com.example.taskManagementSystem.domain.entities.TokenRedisEntity;
import com.example.taskManagementSystem.repositories.TokenRepository;
import com.example.taskManagementSystem.security.JwtService;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Contains logic for getting JWT tokens
 */
@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    @Value("${application.security.jwt.expiration}")
    private long accessExpTime;
    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshExpTime;

    /**
     * Регистрирует нового пользователя. Email должен быть уникальным
     * @param request Данные пользователя, указанные при регистрации
     * @return UserJwtAuthenticationResponse - пара из access и refresh токенов
     * @see UserSignUpRequest
     * @see UserJwtAuthenticationResponse
     * @throws ValidationException При несоответствии правилам полей
     */
    public UserJwtAuthenticationResponse register(UserSignUpRequest request) {
        UserEntity user = userService.create(UserEntity.builder()
                .email(request.getEmail().strip())
                .password(passwordEncoder.encode(request.getPassword()))
                .build());

        return generateTokenResponse(user);
    }

    /**
     * Аутентифицирует существующего пользователя в системе и возвращает пару из access и refresh токенов
     * @param request Данные пользователя, указанные при входе
     * @return UserJwtAuthenticationResponse - пара из новых access и refresh токенов
     * @see UserSignInRequest
     * @see UserJwtAuthenticationResponse
     */
    public UserJwtAuthenticationResponse authenticate(UserSignInRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));
        UserEntity user = userService.getByEmail(request.getEmail());
        return generateTokenResponse(user);
    }

    /**
     * Выпускает новую пару access и refresh токенов по входящему refresh токену
     * @param request Refresh токен, передаваемый в теле запроса
     * @return UserJwtAuthenticationResponse - пара из новых access и refresh токенов
     * @see UserRefreshTokenRequest
     * @see UserJwtAuthenticationResponse
     */
    public UserJwtAuthenticationResponse refreshToken(UserRefreshTokenRequest request) {
        String oldRefreshToken = request.getRefreshToken();

        TokenRedisEntity token = tokenRepository.getByToken(oldRefreshToken)
                .orElseThrow(() -> new AccessDeniedException("Token is invalid or expired"));

        if (!token.getTokenType().equals("REFRESH")) {
            throw new AccessDeniedException("Invalid token type. Expected REFRESH. Found " + token.getTokenType());
        }

        String email = jwtService.extractEmail(oldRefreshToken);
        if (email == null) {
            throw new AccessDeniedException("Provided refresh token is invalid");
        }

        UserEntity user = userService.getByEmail(email);
        tokenRepository.deleteAllByUserId(user.getUserId().toString());
        return generateTokenResponse(user);
    }

    private UserJwtAuthenticationResponse generateTokenResponse(UserEntity user) {
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        tokenRepository.save(new TokenRedisEntity(accessToken, accessExpTime, user.getUserId().toString() ,  "ACCESS"));
        tokenRepository.save(new TokenRedisEntity(refreshToken,refreshExpTime, user.getUserId().toString(), "REFRESH"));

        return UserJwtAuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
