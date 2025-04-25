package com.example.taskManagementSystem.domain.dto.requests;

import com.example.taskManagementSystem.domain.entities.TaskEntity;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

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
            example = "HIGH,MEDIUM,LOW,DEFAULT"
    )
    private TaskEntity.Priority[] priority;

    @Schema(
            description = "Фильтр по ID пользователей, создавших задачи. Можно передать как одиночный ID (например, 1), так и список ID (например, 1,2,3).",
            example = "1,2",
            type = "array"
    )
    @ArraySchema(schema = @Schema(type = "integer", format = "int64"))
    private List<Long> createdByUserId;

    @Schema(
            description = "Фильтр по ID пользователей, которым назначены задачи. " +
                    "Можно передать как одиночный ID (например, 1), так и список ID (например, 1,2,3).",
            example = "1,2",
            type = "array")
    @ArraySchema(schema = @Schema(type = "integer", format = "int64"))
    private List<Long> assignedToUserId;

    @Schema(
            description = "Фильтр по месяцу для календаря",
            example = "04.2025"
    )
    private String monthAndYear;
}