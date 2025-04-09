package com.example.taskManagementSystem.security;

import com.example.taskManagementSystem.domain.dto.responses.ErrorResponse;
import com.example.taskManagementSystem.domain.dto.responses.UserDetailResponse;
import com.example.taskManagementSystem.exceptions.AccessDeniedException;
import com.example.taskManagementSystem.mappers.Mapper;
import com.example.taskManagementSystem.mappers.impl.UserDetailMapperImpl;
import com.example.taskManagementSystem.services.AuthClientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    public static final String BEARER_PREFIX = "Bearer ";
    public static final String HEADER_NAME = "Authorization";
    private final AuthClientService authClientService;
    private final UserDetailServiceImpl userDetailsService;
    private final Mapper<UserDetails, UserDetailResponse> mapper;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws IOException {
        try {
            final String authHeader = request.getHeader(HEADER_NAME);
            if (authHeader == null || !StringUtils.startsWith(authHeader, BEARER_PREFIX)) {
                filterChain.doFilter(request, response);
                return;
            }

            String jwtToken = authHeader.substring(BEARER_PREFIX.length());
            if (StringUtils.isBlank(jwtToken) || !authClientService.validateToken()) {
                filterChain.doFilter(request, response);
                return;
            }

            UserDetails userDetails = userDetailsService.loadUserByUsername(authHeader);
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()));
            filterChain.doFilter(request, response);
        } catch (Exception ex) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json");
            ErrorResponse errorResponse = ErrorResponse.builder()
                    .id(UUID.randomUUID())
                    .message(ex.getMessage())
                    .build();
            String json = new ObjectMapper().writeValueAsString(errorResponse);
            response.getWriter().write(json);
        }
    }
}