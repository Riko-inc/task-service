package com.example.taskManagementSystem.domain.dto.requests;
import com.example.taskManagementSystem.domain.entities.TaskEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "Запрос на создание новой задачи")
public class TaskCreateRequest {
    @Schema(description = "Заголовок задачи", example = "Купить молоко")
    @Size(max = 255, message = "Заголовок должен быть не длиннее 255 символов")
    @NotBlank(message = "Название задачи не может быть пустым")
    private String title;

    @Schema(description = "Описание задачи", example = "Бренд: домик в деревне, главное не перепутать!")
    private String description;

    @Schema(description = "Статус задачи", example = "NEW", defaultValue = "NEW")
    private TaskEntity.Status status = TaskEntity.Status.NEW;

    @Schema(description = "Приоритет задачи", example = "LOW", defaultValue = "DEFAULT")
    private TaskEntity.Priority priority = TaskEntity.Priority.DEFAULT;

    @Schema(description = "Id пользователя, кому назначена задача", example = "1", nullable = true)
    private Long assignedToUserId;

    @Schema(type = "string", description = "Дата дедлайна", example = "14-05-2024 20:50")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime dueTo;
}