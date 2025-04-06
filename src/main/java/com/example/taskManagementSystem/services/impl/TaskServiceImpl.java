package com.example.taskManagementSystem.services.impl;

import com.example.taskManagementSystem.domain.dto.requests.TaskCreateRequest;
import com.example.taskManagementSystem.domain.dto.requests.TaskUpdateRequest;
import com.example.taskManagementSystem.domain.entities.TaskEntity;
import com.example.taskManagementSystem.domain.entities.UserEntity;
import com.example.taskManagementSystem.exceptions.EntityNotFoundException;
import com.example.taskManagementSystem.exceptions.InvalidRequestParameterException;
import com.example.taskManagementSystem.repositories.TaskRepository;
import com.example.taskManagementSystem.repositories.UserRepository;
import com.example.taskManagementSystem.services.AuthClientService;
import com.example.taskManagementSystem.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;

/**
 * Contains logic for operations with Task Entity
 */
@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final AuthClientService authService;

    @Override
    @Transactional
    public TaskEntity createTask(UserEntity user, TaskCreateRequest taskRequest) {
        if (EnumSet.allOf(TaskEntity.Priority.class).stream()
                .noneMatch(e -> e.equals(taskRequest.getPriority()))) {
            throw new InvalidRequestParameterException("Priority should be one of the given: " + Arrays.toString(TaskEntity.Priority.values()));
        }

        if (EnumSet.allOf(TaskEntity.Status.class).stream()
                .noneMatch(e -> e.equals(taskRequest.getStatus()))) {
            throw new InvalidRequestParameterException("Status should be one of the given: " + Arrays.toString(TaskEntity.Status.values()));
        }

        if (!authService.checkUserIdExists(taskRequest.getAssignedToUserId())) {
            throw new EntityNotFoundException("Assigned user with id " + taskRequest.getAssignedToUserId() + " was not found");
        }

        TaskEntity taskEntity = TaskEntity.builder()
                .title(taskRequest.getTitle())
                .description(taskRequest.getDescription())
                .dueTo(taskRequest.getDueTo())
                .priority(taskRequest.getPriority())
                .createdByUserId(user.getUserId())
                .assignedUserId(taskRequest.getAssignedToUserId())
                .status(TaskEntity.Status.NEW)
                .createdDate(LocalDateTime.now())
                .build();
        return taskRepository.save(taskEntity);
    }

    @Override
    @Transactional
    public TaskEntity updateTask(TaskUpdateRequest taskUpdateRequest) {
        TaskEntity taskEntity = taskRepository.findById(taskUpdateRequest.getTaskId())
                .orElseThrow(() -> new EntityNotFoundException("Задача " + taskUpdateRequest.getTaskId() + " не найдена"));

        if (EnumSet.allOf(TaskEntity.Priority.class).stream()
                .noneMatch(e -> e.equals(taskUpdateRequest.getPriority()))) {
            throw new InvalidRequestParameterException("Priority should be one of the given: " + Arrays.toString(TaskEntity.Priority.values()));
        }

        if (EnumSet.allOf(TaskEntity.Status.class).stream()
                .noneMatch(e -> e.equals(taskUpdateRequest.getStatus()))) {
            throw new InvalidRequestParameterException("Status should be one of the given: " + Arrays.toString(TaskEntity.Status.values()));
        }

        if (!authService.checkUserIdExists(taskUpdateRequest.getAssignedToUserId())) {
            throw new EntityNotFoundException("Assigned user with id " + taskUpdateRequest.getAssignedToUserId() + " was not found");
        }



        if (taskUpdateRequest.getDueTo() != null) {
            taskEntity.setDueTo(taskUpdateRequest.getDueTo());
        }
        if (taskUpdateRequest.getAssignedToUserId() != null) {
            taskEntity.setAssignedUserId(taskUpdateRequest.getAssignedToUserId());
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
        return taskEntity;
    }


    @Override
    @Transactional
    public TaskEntity getTaskById(long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Задача " + id + " не найдена"));
    }

    @Override
    @Transactional
    public List<TaskEntity> getAllTasks(long userId, Pageable pageable, Specification<TaskEntity> specification) {
        return taskRepository.findAll(specification, pageable)
                .getContent()
                .stream()
                .filter(task -> Objects.equals(task.getCreatedByUserId(), userId) ||
                        Objects.equals(task.getAssignedUserId(), userId))
                .toList();
    }

    @Override
    @Transactional
    public List<TaskEntity> getAllTasksByUserId(long id, Pageable pageable, Specification<TaskEntity> specification) {
        return getAllTasks(id, pageable, specification);
    }

    @Override
    @Transactional
    public TaskEntity updateTaskStatus(long taskId, TaskEntity.Status taskStatus){
        if (EnumSet.allOf(TaskEntity.Status.class).stream()
                .noneMatch(e -> e.equals(taskStatus))) {
            throw new InvalidRequestParameterException("Status should be one of the given: " + Arrays.toString(TaskEntity.Status.values()));
        }
        TaskEntity taskEntity = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Задача с заданным id " + taskId + " не найдена"));

        taskEntity.setStatus(taskStatus);
        taskRepository.saveAndFlush(taskEntity);
        return taskEntity;
    }

    @Override
    @Transactional
    public void deleteTaskById(long id) {
        taskRepository.deleteTaskByTaskId(id);
    }
}
