package com.example.taskManagementSystem.mappers.impl;

import com.example.taskManagementSystem.domain.dto.responses.TaskResponse;
import com.example.taskManagementSystem.domain.entities.TaskEntity;
import com.example.taskManagementSystem.mappers.Mapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TaskResponseMapperImpl implements Mapper<TaskEntity, TaskResponse> {

    private final ModelMapper modelMapper;

    @Override
    public TaskResponse mapToDto(TaskEntity taskEntity) {
        return modelMapper.map(taskEntity, TaskResponse.class);
    }

    @Override
    public TaskEntity mapFromDto(TaskResponse taskResponse) {
        return modelMapper.map(taskResponse, TaskEntity.class);
    }
}
