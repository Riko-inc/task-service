package com.example.taskManagementSystem.controllers;

import com.example.taskManagementSystem.domain.dto.requests.TaskCreateRequest;
import com.example.taskManagementSystem.domain.dto.requests.TaskGetAllRequest;
import com.example.taskManagementSystem.domain.dto.requests.TaskStatusUpdateRequest;
import com.example.taskManagementSystem.domain.dto.requests.TaskUpdateRequest;
import com.example.taskManagementSystem.domain.dto.responses.TaskResponse;
import com.example.taskManagementSystem.domain.entities.TaskEntity;
import com.example.taskManagementSystem.domain.entities.UserEntity;
import com.example.taskManagementSystem.mappers.Mapper;
import com.example.taskManagementSystem.services.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
@Validated
@Tag(name = "Работа с задачами")
public class TaskController {
    private final TaskService taskService;
    private final Mapper<TaskEntity, TaskResponse> taskMapper;

    @Operation(summary = "Создать новую задачу")
    @SecurityRequirement(name = "JWT")
    @PostMapping(path = "/task")
    public ResponseEntity<TaskResponse> createTask(@AuthenticationPrincipal UserEntity user, @RequestBody @Valid TaskCreateRequest taskRequest){
        TaskEntity savedTaskEntity = taskService.createTask(user, taskRequest);
        return new ResponseEntity<>(taskMapper.mapToDto(savedTaskEntity), HttpStatus.CREATED);
    }

    @Operation(summary = "Получить список задач в пространстве")
    @SecurityRequirement(name = "JWT")
    @GetMapping(path = "/task")
    public ResponseEntity<List<TaskResponse>> getAllTasksInSpace(@AuthenticationPrincipal UserEntity user, @Valid TaskGetAllRequest request) {
        List<TaskEntity> queryResult = taskService.getAllTasksInSpace(user, request);
        return new ResponseEntity<>(queryResult.stream().map(taskMapper::mapToDto).toList(), HttpStatus.OK);
    }

    @Operation(summary = "Получить задачу по её id")
    @SecurityRequirement(name = "JWT")
    @GetMapping(path = "/task/{id}")
    @Deprecated
    public ResponseEntity<TaskResponse> getTaskById(@PathVariable Long id) {
        TaskEntity savedTaskEntity = taskService.getTaskById(id);
        return new ResponseEntity<>(taskMapper.mapToDto(savedTaskEntity), HttpStatus.OK);
    }

    @Operation(summary = "Изменить задачу")
    @SecurityRequirement(name = "JWT")
    @PutMapping(path = "/task")
    @PreAuthorize("@AccessService.canChangeTask(principal, #taskUpdateRequest.getTaskId())")
    public ResponseEntity<TaskResponse> updateTask(@RequestBody @Valid TaskUpdateRequest taskUpdateRequest) {
        TaskEntity taskEntity = taskService.updateTask(taskUpdateRequest);
        return new ResponseEntity<>(taskMapper.mapToDto(taskEntity), HttpStatus.OK);
    }

    @Operation(summary = "Изменить статус задачи")
    @SecurityRequirement(name = "JWT")
    @PatchMapping("/task/status")
    @PreAuthorize("@AccessService.canChangeStatus(principal, #request.getTaskId())")
    public ResponseEntity<TaskResponse> patchTaskStatus(@RequestBody @Valid TaskStatusUpdateRequest request) {
        TaskEntity taskEntity = taskService.updateTaskStatus(request.getTaskId(), request.getStatus());
        return new ResponseEntity<>(taskMapper.mapToDto(taskEntity), HttpStatus.OK);
    }

    @Operation(summary = "Удалить задачу")
    @SecurityRequirement(name = "JWT")
    @DeleteMapping(path = "/task/{id}")
    @PreAuthorize("@AccessService.canChangeTask(principal, #id)")
    public ResponseEntity<HttpStatus> deleteTask(@PathVariable long id) {
        taskService.deleteTaskById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

