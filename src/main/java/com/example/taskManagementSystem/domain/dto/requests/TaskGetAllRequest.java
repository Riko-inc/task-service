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

    @NotNull(message = "Номер страницы не может быть пустым")
    @Schema(description = "Номер страницы", defaultValue = "0", example = "0")
    private Integer page = 0;

    @NotNull(message = "Размер страницы не может быть пустым")
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

    @ArraySchema(
            schema = @Schema(implementation = TaskEntity.Status.class),
            arraySchema = @Schema(
                    description = "Фильтр по статусам задач. Можно указать несколько значений.",
                    example = "[\"NEW\",\"IN_PROGRESS\"]"
            )
    )
    private List<TaskEntity.Status> status;

    @ArraySchema(
            schema = @Schema(implementation = TaskEntity.Priority.class),
            arraySchema = @Schema(
                    description = "Фильтр по приоритетам задач. Можно указать несколько значений.",
                    example = "[\"HIGH\",\"MEDIUM\",\"LOW\",\"DEFAULT\"]"
            )
    )
    private List<TaskEntity.Priority> priority;

    @ArraySchema(
            schema = @Schema(type = "integer", format = "int64"),
            arraySchema = @Schema(
                    description = "Фильтр по ID пользователей, создавших задачи. " +
                            "Можно передать как одиночный ID (например, 1), " +
                            "так и список ID (например, [1,2,3]).",
                    example = "[1,2]"
            )
    )
    private List<Long> createdByUserId;

    @ArraySchema(
            schema = @Schema(type = "integer", format = "int64"),
            arraySchema = @Schema(
                    description = "Фильтр по ID пользователей, которым назначены задачи. " +
                            "Можно передать как одиночный ID (например, 1), " +
                            "так и список ID (например, [1,2,3]).",
                    example = "[1,2]"
            )
    )
    private List<Long> assignedToUserId;

    @Schema(
            description = "Фильтр по месяцу для календаря",
            example = "04.2025"
    )
    private String monthAndYear;
}