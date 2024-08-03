package com.example.taskManagementSystem.mappers.impl;

import com.example.taskManagementSystem.domain.dto.TaskDto;
import com.example.taskManagementSystem.domain.entities.TaskEntity;
import com.example.taskManagementSystem.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TaskMapperImpl implements Mapper<TaskEntity, TaskDto> {

    private final ModelMapper modelMapper;

    public TaskMapperImpl(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    @Override
    public TaskDto mapToDto(TaskEntity taskEntity) {
        return modelMapper.map(taskEntity, TaskDto.class);
    }

    @Override
    public TaskEntity mapFromDto(TaskDto taskDto) {
        return modelMapper.map(taskDto, TaskEntity.class);
    }
}

