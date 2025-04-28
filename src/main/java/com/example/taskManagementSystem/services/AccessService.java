package com.example.taskManagementSystem.services;

import com.example.taskManagementSystem.domain.entities.CommentEntity;
import com.example.taskManagementSystem.domain.entities.TaskEntity;
import com.example.taskManagementSystem.domain.entities.UserEntity;
import com.example.taskManagementSystem.exceptions.EntityNotFoundException;
import com.example.taskManagementSystem.repositories.CommentRepository;
import com.example.taskManagementSystem.repositories.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * Defines assess rules for users needed to perform actions
 */
@Service("AccessService")
@RequiredArgsConstructor
@Slf4j
public class AccessService {

    private final TaskRepository taskRepository;
    private final CommentRepository commentRepository;

    /**
     * Проверяет, может ли пользователь выполнять операцию изменения задачи (Например, изменения названия или описания)
     * @param userEntity Пользователь, получаемый из Principal
     * @param taskId id задачи
     * @return True/False
     */
    public boolean canChangeTask(UserEntity userEntity, long taskId) {
        TaskEntity task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task with id " + taskId + " was not found"));

        log.info("Task found: {}", task);
        log.info("UserId: {}", userEntity.getUserId());
        log.info("Can change task: {}", Objects.equals(userEntity.getUserId(), task.getCreatedByUserId()));
        return Objects.equals(userEntity.getUserId(), task.getCreatedByUserId());
    }

    /**
     * Проверяет, может ли пользователь выполнять операцию изменения статуса задачи TaskEntity. Статус задачи может
     * изменять как владелец задачи, так и человек, которому она была назначена
     * @param userEntity Пользователь, получаемый из Principal
     * @param taskId id задачи
     * @return True/False
     */
    public boolean canChangeStatus(UserEntity userEntity, long taskId) {
        TaskEntity task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task with id " + taskId + " was not found"));
        return (Objects.equals(userEntity.getUserId(), task.getCreatedByUserId()) ||
                Objects.equals(userEntity.getUserId(), task.getAssignedUserId()));
    }

    /**
     * Проверяет, может ли пользователь выполнять операцию изменения статуса комментария CommentEntity
     * @param userEntity Пользователь, получаемый из Principal
     * @param commentId id комментария
     * @return True/False
     */
    public boolean canChangeComment(UserEntity userEntity, long commentId) {
        CommentEntity comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Comment with id " + commentId + " was not found"));;
        return Objects.equals(comment.getAuthorId(), userEntity.getUserId());
    }
}
