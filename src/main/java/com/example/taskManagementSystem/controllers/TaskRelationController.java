package com.example.taskManagementSystem.controllers;
import com.example.taskManagementSystem.domain.dto.requests.TaskRelationCreateRequest;
import com.example.taskManagementSystem.domain.dto.requests.TaskRelationUpdateRequest;
import com.example.taskManagementSystem.domain.dto.responses.TaskRelationResponse;
import com.example.taskManagementSystem.domain.entities.UserEntity;
import com.example.taskManagementSystem.services.TaskRelationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/relations")
@RequiredArgsConstructor
@Validated
@Tag(name = "Работа с отношениями между задачами")
public class TaskRelationController {
    private final TaskRelationService taskRelationService;

    @Operation(summary = "Создать новое отношение между задачами")
    @SecurityRequirement(name = "JWT")
    @PostMapping
    public ResponseEntity<TaskRelationResponse> createTaskRelation(@AuthenticationPrincipal UserEntity user,
                                                           @RequestBody @Valid TaskRelationCreateRequest request) {
        return new ResponseEntity<>(taskRelationService.createTaskRelation(request), HttpStatus.CREATED);
    }

    @Operation(summary = "Получить все отношения задачи, в которых она является родительской")
    @SecurityRequirement(name = "JWT")
    @GetMapping("/{taskId}")
    public ResponseEntity<List<TaskRelationResponse>> getTaskRelations(@AuthenticationPrincipal UserEntity user,
                                                                       @PathVariable @NotNull Long taskId) {
        return new ResponseEntity<>(taskRelationService.getTaskRelations(taskId), HttpStatus.OK);
    }

    @Operation(summary = "Обновить отношение")
    @SecurityRequirement(name = "JWT")
    @PutMapping
    public ResponseEntity<TaskRelationResponse> updateRelation(@AuthenticationPrincipal UserEntity user,
                                                                     @RequestBody @Valid TaskRelationUpdateRequest request) {
        return new ResponseEntity<>(taskRelationService.updateTaskRelation(request), HttpStatus.OK);
    }

    @Operation(summary = "Удалить отношение")
    @SecurityRequirement(name = "JWT")
    @DeleteMapping("/{relationId}")
    public ResponseEntity<HttpStatus> deleteRelation(@AuthenticationPrincipal UserEntity user,
                                                               @PathVariable @NotNull Long relationId) {
        taskRelationService.deleteTaskRelation(relationId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
