package com.example.taskManagementSystem.domain.dto.requests;

import com.example.taskManagementSystem.domain.entities.TaskEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Запрос на получение всех задач пользователя по его id")
public class TaskGetAllRequest {

    @NotNull
    @Schema(description = "Номер страницы", defaultValue = "0", example = "0")
    private Integer page = 0;

    @NotNull
    @Min(1)
    @Max(100)
    @Schema(description = "Размер страницы", defaultValue = "50", example = "50")
    private Integer size = 50;

    @Schema(
            description = "Поле и направление сортировки (формат: поле,направление)",
            defaultValue = "taskId,asc",
            example = "priority,desc"
    )
    private String sort = "taskId,asc";

    @Schema(
            description = "Фильтр по статусам (можно несколько значений)",
            implementation = TaskEntity.Status.class,
            type = "array",
            example = "NEW,IN_PROGRESS"
    )
    private TaskEntity.Status[] status;

    @Schema(
            description = "Фильтр по приоритетам (можно несколько значений)",
            implementation = TaskEntity.Priority.class,
            type = "array",
            example = "HIGH,MEDIUM"
    )
    private TaskEntity.Priority[] priority;
}