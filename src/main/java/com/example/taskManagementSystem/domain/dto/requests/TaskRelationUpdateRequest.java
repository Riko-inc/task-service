package com.example.taskManagementSystem.domain.dto.requests;

import com.example.taskManagementSystem.domain.enums.RelationType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Запрос на обновление связи между задачами")
public class TaskRelationUpdateRequest {
    @Schema(description = "Id отношения, которое нужно обновить")
    private Long relationId;

    @Schema(description = "Отношение задачи fromTask к toTask", example = "PARENT", defaultValue = "RELATES_TO")
    private RelationType relation = RelationType.RELATES_TO;

    @Schema(description = "Id родительской задачи", example = "1", defaultValue = "1")
    @NotNull
    private Long fromTaskId;

    @Schema(description = "Id дочерней задачи", example = "1", defaultValue = "1")
    @NotNull
    private Long toTaskId;
}

