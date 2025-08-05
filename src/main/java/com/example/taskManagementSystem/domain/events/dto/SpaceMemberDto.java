package com.example.taskManagementSystem.domain.events.dto;

import com.example.taskManagementSystem.domain.events.enums.SpaceMemberRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SpaceMemberDto {
    private Long spaceMemberId;
    private Long userId;                    // Совпадает с userId из auth-service
    private SpaceMemberRole spaceMemberRole;
    private LocalDateTime invitedDateTime;
    private Long invitedBySpaceMemberId;
    private Long workspaceId;
}
