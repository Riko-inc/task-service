//package com.example.taskManagementSystem.config;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.context.event.ApplicationReadyEvent;
//import org.springframework.cache.annotation.EnableCaching;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.event.EventListener;
//import org.springframework.data.redis.core.RedisTemplate;
//
//import java.util.Set;
//
//@Configuration
//@RequiredArgsConstructor
//@Slf4j
//public class RedisCacheCleaner {
//    @Value("${spring.cache.redis.key-prefix}")
//    private String cachePrefix;
//
//    private final RedisTemplate<String, Object> redisTemplate;
//
//    @EventListener(ApplicationReadyEvent.class)
//    public void onApplicationReady() {
//        Set<String> keys = redisTemplate.keys(cachePrefix);
//        redisTemplate.delete(keys);
//    }
//
//}
