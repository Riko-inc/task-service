package com.example.taskManagementSystem.services.impl;

import com.example.taskManagementSystem.domain.dto.requests.TaskCreateRequest;
import com.example.taskManagementSystem.domain.dto.requests.TaskUpdateRequest;
import com.example.taskManagementSystem.domain.entities.TaskEntity;
import com.example.taskManagementSystem.domain.entities.UserEntity;
import com.example.taskManagementSystem.repositories.TaskRepository;
import com.example.taskManagementSystem.repositories.UserRepository;
import com.example.taskManagementSystem.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Override
    public TaskEntity createTask(UserEntity user, TaskCreateRequest taskRequest) {
        TaskEntity taskEntity = TaskEntity.builder()
                .title(taskRequest.getTitle())
                .description(taskRequest.getDescription())
                .endDeadline(taskRequest.getDueToEnd())
                .priority(taskRequest.getPriority())
                .createdByUser(user)
                .assignedUser(userRepository.findByEmail(taskRequest.getAssignedToEmail()).orElse(null))
                .status(TaskEntity.Status.NEW)
                .createdDate(LocalDateTime.now())
                .build();
        return taskRepository.save(taskEntity);
    }

    @Override
    public Optional<TaskEntity> updateTask(TaskUpdateRequest taskUpdateRequest) {
        Optional<TaskEntity> savedTaskEntity = taskRepository.findById(taskUpdateRequest.getTaskId());

        if (savedTaskEntity.isEmpty()) {
            return Optional.empty();
        }

        TaskEntity taskEntity = savedTaskEntity.get();
        if (taskUpdateRequest.getDueToEnd() != null) {
            taskEntity.setEndDeadline(taskUpdateRequest.getDueToEnd());
        }
        if (taskUpdateRequest.getTitle() != null) {
            taskEntity.setTitle(taskUpdateRequest.getTitle());
        }
        if (taskUpdateRequest.getDescription() != null) {
            taskEntity.setDescription(taskUpdateRequest.getDescription());
        }
        if (taskUpdateRequest.getPriority() != taskEntity.getPriority()) {
            taskEntity.setPriority(taskUpdateRequest.getPriority());
        }
        if (taskUpdateRequest.getStatus() != taskEntity.getStatus()) {
            taskEntity.setStatus(taskUpdateRequest.getStatus());
        }
        taskRepository.saveAndFlush(taskEntity);
        return Optional.of(taskEntity);
    }


    @Override
    public Optional<TaskEntity> getTaskById(long id) {
        return taskRepository.findById(id);
    }

    @Override
    public List<TaskEntity> getAllTasks(UserEntity user, Pageable pageable, Specification<TaskEntity> specification) {
        return taskRepository.findAll(specification, pageable)
                .getContent()
                .stream()
                .filter(task -> Objects.equals(task.getCreatedByUser().getUserId(), user.getUserId()))
                .toList();
    }

    @Override
    public void deleteTaskById(long id) {
        taskRepository.deleteTaskByTaskId(id);
    }
}
