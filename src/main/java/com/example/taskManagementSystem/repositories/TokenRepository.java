package com.example.taskManagementSystem.repositories;

import java.util.Optional;

import com.example.taskManagementSystem.domain.entities.TokenRedisEntity;
import org.springframework.data.repository.CrudRepository;

public interface TokenRepository extends CrudRepository<TokenRedisEntity, String> {

    Optional<TokenRedisEntity> getByToken(String token);
    void deleteAllByUserId(String userId);
    void deleteByToken(String token);
}