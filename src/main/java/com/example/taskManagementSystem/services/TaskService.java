package com.example.taskManagementSystem.services;

import com.example.taskManagementSystem.domain.dto.TaskDto;
import com.example.taskManagementSystem.domain.dto.requests.TaskCreateRequest;
import com.example.taskManagementSystem.domain.dto.requests.TaskUpdateRequest;
import com.example.taskManagementSystem.domain.entities.TaskEntity;
import com.example.taskManagementSystem.domain.entities.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface TaskService {
    TaskEntity createTask(UserEntity user, TaskCreateRequest taskRequest);
    Optional<TaskEntity> updateTask(TaskUpdateRequest taskUpdateRequest);
    Optional<TaskEntity> getTaskById(long id);
    List<TaskEntity> getAllTasks(UserEntity user, Pageable pageable, Specification<TaskEntity> specification);
    void deleteTaskById(long id);
}
