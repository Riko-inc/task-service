package com.example.taskManagementSystem.mappers.impl;

import com.example.taskManagementSystem.domain.dto.responses.TaskRelationResponse;
import com.example.taskManagementSystem.domain.entities.TaskRelationEntity;
import com.example.taskManagementSystem.mappers.Mapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TaskRelationResponseMapperImpl implements Mapper<TaskRelationEntity, TaskRelationResponse> {

    private final ModelMapper modelMapper;

    @Override
    public TaskRelationResponse mapToDto(TaskRelationEntity taskRelationEntity) {
        return modelMapper.map(taskRelationEntity, TaskRelationResponse.class);
    }

    @Override
    public TaskRelationEntity mapFromDto(TaskRelationResponse taskRelationResponse) {
        return modelMapper.map(taskRelationResponse, TaskRelationEntity.class);
    }
}
