package com.example.taskManagementSystem.domain.dto.responses;
import com.example.taskManagementSystem.domain.entities.TaskEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskResponse {
    private long taskId;
    private String title;
    private String description;
    private long createdByUserId;
    private Long assignedToUserId;
    private Double position;
    @Schema(type = "string")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime dueTo;
    private TaskEntity.Status status;
    private TaskEntity.Priority priority;
    @Schema(type = "string")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime createdDate;
}
