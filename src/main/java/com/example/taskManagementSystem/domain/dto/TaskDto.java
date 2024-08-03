package com.example.taskManagementSystem.domain.dto;
import com.example.taskManagementSystem.domain.entities.TaskEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
    private LocalDateTime endDeadline;
    private TaskEntity.Status status;
    private TaskEntity.Priority priority;
    private LocalDateTime createdDate;
}
