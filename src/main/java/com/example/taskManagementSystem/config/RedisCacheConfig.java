package com.example.taskManagementSystem.config;

import com.example.taskManagementSystem.domain.dto.requests.TaskGetAllRequest;
import com.example.taskManagementSystem.domain.entities.UserEntity;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.time.Duration;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator.instance;

@Configuration
@EnableCaching
@RequiredArgsConstructor
@Slf4j
public class RedisCacheConfig {

    @Value("${spring.cache.redis.key-prefix}")
    private String cachePrefix;

    private final RedisConnectionFactory connectionFactory;
    private final Jackson2ObjectMapperBuilder jacksonBuilder;

    @Bean
    public static Module javaTimeModule() {
        return new JavaTimeModule();
    }


    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        ObjectMapper redisMapper = new ObjectMapper();
        redisMapper.registerModule(new JavaTimeModule());
        redisMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        GenericJackson2JsonRedisSerializer redisSerializer =
                new GenericJackson2JsonRedisSerializer(redisMapper);

        RedisTemplate<String, Object> tpl = new RedisTemplate<>();
        tpl.setConnectionFactory(connectionFactory);
        tpl.setKeySerializer(new StringRedisSerializer());
        tpl.setValueSerializer(redisSerializer);
        tpl.setHashKeySerializer(new StringRedisSerializer());
        tpl.setHashValueSerializer(redisSerializer);
        return tpl;
    }

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.activateDefaultTyping(instance, ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer(mapper);

        RedisCacheConfiguration cfg = RedisCacheConfiguration.defaultCacheConfig()
                .prefixCacheNameWith(cachePrefix)
                .entryTtl(Duration.ofMinutes(30))
                .disableCachingNullValues()
                .serializeValuesWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(serializer)
                );
        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(cfg)
                .build();
    }
    @Bean
    public KeyGenerator taskListKeyGenerator() {
        return (target, method, params) -> {
            UserEntity userEntity = (UserEntity) params[0];
            Long userId = userEntity.getUserId();
            TaskGetAllRequest req = (TaskGetAllRequest) params[1];
            return "taskList::" + userId + "::" + req.hashCode();
        };
    }

    @Bean
    public ApplicationListener<ApplicationReadyEvent> cacheCleaner(RedisTemplate<String, Object> tpl) {
        return evt -> {
            String pattern = cachePrefix + "*";
            Set<String> keys = new HashSet<>();
            ScanOptions options = ScanOptions.scanOptions()
                    .match(pattern)
                    .count(1000)
                    .build();
            try (Cursor<byte[]> cursor = Objects.requireNonNull(tpl.getConnectionFactory())
                    .getConnection()
                    .keyCommands()
                    .scan(options)) {
                cursor.forEachRemaining(raw -> keys.add(new String(raw)));
            } catch (Exception ex) {
                log.error("Ошибка при скане ключей", ex);
            }
            if (!keys.isEmpty()) {
                tpl.delete(keys);
                log.info("Удалено {} ключей с префиксом '{}'", keys.size(), cachePrefix);
            } else {
                log.info("Ключи с префиксом '{}' не найдены", cachePrefix);
            }
        };
    }
}
