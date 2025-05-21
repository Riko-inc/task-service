package com.example.taskManagementSystem.domain.dto.requests;
import com.example.taskManagementSystem.domain.entities.TaskEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "Запрос на создание новой задачи")
public class TaskUpdateRequest {
    @Schema(description = "Id задачи", example = "1")
    @NotNull(message = "Id задачи обязателен")
    private Long taskId;

    @Schema(description = "Заголовок задачи", example = "Купить молоко")
    @NotBlank(message = "Заголовок не может быть пустым")
    @Size(max = 255, message = "Длина заголовка - до 255 символов")
    private String title;

    @Schema(description = "Описание задачи", example = "Бренд: домик в деревне, главное не перепутать!")
    private String description;

    @Schema(description = "Место, где находится задача", example = "1")
    private Double position;

    @Schema(description = "Статус задачи", example = "NEW", defaultValue = "NEW")
    @NotNull(message = "Статус задачи не может быть пустым")
    private TaskEntity.Status status;

    @Schema(description = "Приоритет задачи", example = "LOW", defaultValue = "DEFAULT")
    @NotNull(message = "Приоритет задачи не может быть пустым")
    private TaskEntity.Priority priority;

    @Schema(description = "Id пользователя, кому назначена задача", example = "1")
    private Long assignedToUserId;

    @Schema(type = "string", description = "Дата дедлайна", example = "14-05-2024 20:50")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime dueTo;
}