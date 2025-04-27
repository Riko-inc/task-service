package com.example.taskManagementSystem.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.Ordered;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ApplicationConfig {
    @EventListener(ApplicationReadyEvent.class)
    public void onAppStart() {
        System.out.println("Documentation for task-service: http://localhost:8082/swagger-ui/index.html#/");
    }

    @Bean
    public FilterRegistrationBean<RequestLogFilter> requestLoggingFilter() {
        FilterRegistrationBean<RequestLogFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new RequestLogFilter());
        registration.addUrlPatterns("/api/*");
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE + 1);
        return registration;
    }
}

