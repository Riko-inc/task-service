package com.example.taskManagementSystem.mappers.impl;

import com.example.taskManagementSystem.domain.dto.requests.TaskRelationCreateRequest;
import com.example.taskManagementSystem.domain.entities.TaskRelationEntity;
import com.example.taskManagementSystem.mappers.Mapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TaskRelationCreateMapperImpl implements Mapper<TaskRelationEntity, TaskRelationCreateRequest> {

    private final ModelMapper modelMapper;

    @Override
    public TaskRelationCreateRequest mapToDto(TaskRelationEntity taskRelationEntity) {
        return modelMapper.map(taskRelationEntity, TaskRelationCreateRequest.class);
    }

    @Override
    public TaskRelationEntity mapFromDto(TaskRelationCreateRequest taskRelationResponse) {
        return modelMapper.map(taskRelationResponse, TaskRelationEntity.class);
    }
}
