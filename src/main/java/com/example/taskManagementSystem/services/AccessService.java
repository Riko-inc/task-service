package com.example.taskManagementSystem.services;

import com.example.taskManagementSystem.domain.entities.CommentEntity;
import com.example.taskManagementSystem.domain.entities.TaskEntity;
import com.example.taskManagementSystem.domain.entities.UserEntity;
import com.example.taskManagementSystem.repositories.CommentRepository;
import com.example.taskManagementSystem.repositories.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

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
        Optional<TaskEntity> task = taskRepository.findById(taskId);
        log.debug("Task found: {}", task);
        log.debug("UserId: {}", userEntity.getUserId());
        log.debug("Can change task: {}", task.isPresent() && Objects.equals(userEntity.getUserId(), task.get().getCreatedByUserId()));
        return task.isPresent() && Objects.equals(userEntity.getUserId(), task.get().getCreatedByUserId());
    }

    /**
     * Проверяет, может ли пользователь выполнять операцию изменения статуса задачи TaskEntity. Статус задачи может
     * изменять как владелец задачи, так и человек, которому она была назначена
     * @param userEntity Пользователь, получаемый из Principal
     * @param taskId id задачи
     * @return True/False
     */
    public boolean canChangeStatus(UserEntity userEntity, long taskId) {
        Optional<TaskEntity> task = taskRepository.findById(taskId);
        return task.isPresent() && (Objects.equals(userEntity.getUserId(), task.get().getCreatedByUserId()) ||
                Objects.equals(userEntity.getUserId(), task.get().getAssignedUserId()));
    }

    /**
     * Проверяет, может ли пользователь выполнять операцию изменения статуса комментария CommentEntity
     * @param userEntity Пользователь, получаемый из Principal
     * @param commentId id комментария
     * @return True/False
     */
    public boolean canChangeComment(UserEntity userEntity, long commentId) {
        Optional<CommentEntity> comment = commentRepository.findById(commentId);
        return comment.isPresent() && Objects.equals(comment.get().getAuthor().getUserId(), userEntity.getUserId());
    }
}
