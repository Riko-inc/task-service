package com.example.taskManagementSystem.services;

import com.example.taskManagementSystem.domain.dto.requests.TaskCreateRequest;
import com.example.taskManagementSystem.domain.dto.requests.TaskUpdateRequest;
import com.example.taskManagementSystem.domain.entities.TaskEntity;
import com.example.taskManagementSystem.domain.entities.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TaskService {
    TaskEntity createTask(UserEntity user, TaskCreateRequest taskRequest);
    TaskEntity updateTask(TaskUpdateRequest taskUpdateRequest);
    TaskEntity getTaskById(long id);
    List<TaskEntity> getAllTasks(UserEntity user, Pageable pageable, Specification<TaskEntity> specification);
    List<TaskEntity> getAllTasksByUserId(long id, Pageable pageable, Specification<TaskEntity> specification);
    void deleteTaskById(long id);
    TaskEntity updateTaskStatus(long taskId, TaskEntity.Status taskStatus);
}
