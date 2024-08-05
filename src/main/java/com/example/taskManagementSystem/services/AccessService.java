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

@Service("AccessService")
@RequiredArgsConstructor
@Slf4j
public class AccessService {

    private final TaskRepository taskRepository;
    private final CommentRepository commentRepository;

    public boolean canChangeTask(UserEntity userEntity, long taskId) {
        Optional<TaskEntity> task = taskRepository.findById(taskId);
        return task.isPresent() && Objects.equals(userEntity.getUserId(), task.get().getCreatedByUser().getUserId());
    }

    public boolean canChangeStatus(UserEntity userEntity, long taskId) {
        Optional<TaskEntity> task = taskRepository.findById(taskId);
        return task.isPresent() && (Objects.equals(userEntity.getUserId(), task.get().getCreatedByUser().getUserId()) ||
                Objects.equals(userEntity.getUserId(), task.get().getAssignedUser().getUserId()));
    }

    public boolean canChangeComment(UserEntity userEntity, long commentId) {
        Optional<CommentEntity> comment = commentRepository.findById(commentId);
        return comment.isPresent() && Objects.equals(comment.get().getAuthor().getUserId(), userEntity.getUserId());
    }
}
