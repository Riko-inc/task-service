package com.example.taskManagementSystem.services;

import com.example.taskManagementSystem.domain.dto.requests.TaskRelationCreateRequest;
import com.example.taskManagementSystem.domain.dto.requests.TaskRelationUpdateRequest;
import com.example.taskManagementSystem.domain.dto.responses.TaskRelationResponse;

import java.util.List;

public interface TaskRelationService {

    List<TaskRelationResponse> getTaskRelations(Long taskId);
    TaskRelationResponse createTaskRelation(TaskRelationCreateRequest request);
    TaskRelationResponse updateTaskRelation(TaskRelationUpdateRequest request);
    void deleteTaskRelation(Long taskRelationId);
}
