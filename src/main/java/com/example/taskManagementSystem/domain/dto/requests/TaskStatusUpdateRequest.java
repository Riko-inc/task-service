package com.example.taskManagementSystem.domain.dto.requests;

import com.example.taskManagementSystem.domain.entities.TaskEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Запрос на создание новой задачи")
public class TaskStatusUpdateRequest {
    @Schema(description = "Id задачи", example = "12")
    @NotNull(message = "Id задачи не может быть пустым")
    private long taskId;

    @Schema(description = "Статус задачи", example = "NEW")
    @NotNull(message = "Статус задачи не может быть пустым")
    private TaskEntity.Status status;
}