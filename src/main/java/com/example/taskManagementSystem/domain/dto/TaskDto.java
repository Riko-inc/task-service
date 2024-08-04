package com.example.taskManagementSystem.domain.dto;
import com.example.taskManagementSystem.domain.entities.TaskEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskDto {
    private long taskId;
    private long createdByUserId;
    private Long assignedToUserId;
    private String title;
    private String description;
    @Schema(type = "string")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime dueTo;
    private TaskEntity.Status status;
    private TaskEntity.Priority priority;
    private List<CommentDto> comments;
    @Schema(type = "string")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime createdDate;
}
