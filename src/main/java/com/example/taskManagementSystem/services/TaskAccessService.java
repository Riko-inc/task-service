package com.example.taskManagementSystem.services;

import com.example.taskManagementSystem.domain.entities.TaskEntity;
import com.example.taskManagementSystem.domain.entities.UserEntity;
import com.example.taskManagementSystem.repositories.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service("taskAccessService")
@RequiredArgsConstructor
public class TaskAccessService {

    private final TaskRepository taskRepository;

    public boolean canChangeTask(UserEntity userEntity, long taskId) {
        Optional<TaskEntity> task = taskRepository.findById(taskId);
        return task.isPresent() && Objects.equals(userEntity.getUserId(), task.get().getCreatedByUser().getUserId());
    }
}
