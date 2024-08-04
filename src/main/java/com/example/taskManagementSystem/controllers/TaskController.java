package com.example.taskManagementSystem.controllers;

import com.example.taskManagementSystem.domain.dto.TaskDto;
import com.example.taskManagementSystem.domain.dto.requests.TaskCreateRequest;
import com.example.taskManagementSystem.domain.dto.requests.TaskUpdateRequest;
import com.example.taskManagementSystem.domain.entities.TaskEntity;
import com.example.taskManagementSystem.domain.entities.UserEntity;
import com.example.taskManagementSystem.mappers.Mapper;
import com.example.taskManagementSystem.controllers.specifications.TaskSpecifications;
import com.example.taskManagementSystem.services.TaskService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/task")
@RequiredArgsConstructor
@Tag(name = "Работа с задачами")
public class TaskController {
    private final TaskService taskService;
    private final Mapper<TaskEntity, TaskDto> taskMapper;

    @PostMapping
    public ResponseEntity<TaskDto> createTask(@AuthenticationPrincipal UserEntity user, @RequestBody TaskCreateRequest taskRequest){
        TaskEntity savedTaskEntity = taskService.createTask(user, taskRequest);
        return new ResponseEntity<>(taskMapper.mapToDto(savedTaskEntity), HttpStatus.CREATED);
    }

    @GetMapping(path = "/tasks")
    public ResponseEntity<List<TaskDto>> getAllTasks(
            @AuthenticationPrincipal UserEntity user,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @RequestParam(defaultValue = "taskId, asc") String[] sort,
            @RequestParam(required = false) TaskEntity.Status status,
            @RequestParam(required = false) TaskEntity.Priority priority) {
        String sortBy = sort[0];
        Sort.Direction direction = Sort.Direction.fromString(sort[1]);
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        Specification<TaskEntity> specification = Specification.where(null);
        if (status != null) {
            specification = specification.and(TaskSpecifications.hasStatus(status));
        }

        if (priority != null) {
            specification = specification.and(TaskSpecifications.hasPriority(priority.ordinal()));
        }

        List<TaskEntity> queryResult = taskService.getAllTasks(user, pageable, specification);

        return new ResponseEntity<>(queryResult.stream().map(taskMapper::mapToDto).toList(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable Long id) {
        Optional<TaskEntity> savedTaskEntity = taskService.getTaskById(id);
        return savedTaskEntity.map(taskEntity -> new ResponseEntity<>(taskMapper.mapToDto(taskEntity), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping
    @PreAuthorize("@AccessService.canChangeTask(principal, #taskUpdateRequest.getTaskId())")
    public ResponseEntity<TaskDto> updateTask(@RequestBody TaskUpdateRequest taskUpdateRequest) {
        Optional<TaskEntity> result = taskService.updateTask(taskUpdateRequest);
        return result.map(taskEntity -> new ResponseEntity<>(taskMapper.mapToDto(taskEntity), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PatchMapping("{id}/status")
    @PreAuthorize("@AccessService.canChangeStatus(principal, #id)")
    public ResponseEntity<TaskDto> patchTaskStatus(@PathVariable long id, @RequestBody TaskEntity.Status newStatus) {
        Optional<TaskEntity> result = taskService.updateTaskStatus(id, newStatus);
        return result.map(taskEntity -> new ResponseEntity<>(taskMapper.mapToDto(taskEntity), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping(path = "/{id}")
    @PreAuthorize("@AccessService.canChangeTask(principal, #id)")
    public ResponseEntity<HttpStatus> deleteTask(@PathVariable long id) {
        taskService.deleteTaskById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

