package com.example.taskManagementSystem.domain.events.dto;

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
public class WorkspaceDto {
    private Long workspaceId;
    private String workspaceName;
    private String workspaceDescription;
    private String taskPrefix;
    private LocalDateTime createdDateTime;
    private LocalDateTime updatedDateTime;
    private List<SpaceMemberDto> members;
}
