package com.example.taskManagementSystem.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import feign.RequestInterceptor;
import feign.RequestTemplate;

@Slf4j
@Configuration
public class FeignConfig implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate template) {
        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (attributes != null) {
            String token = attributes.getRequest().getHeader("Authorization");
            if (token != null) {
                log.debug("Передача заголовка Authorization: {}", token);
                template.header("Authorization", token);
            } else {
                log.warn("Заголовок Authorization отсутствует в запросе");
            }
        } else {
            log.warn("Не удалось получить RequestContextHolder с атрибутами запроса");
        }
    }
}
