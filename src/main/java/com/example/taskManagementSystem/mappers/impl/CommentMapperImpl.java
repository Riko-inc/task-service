package com.example.taskManagementSystem.mappers.impl;

import com.example.taskManagementSystem.domain.dto.CommentDto;
import com.example.taskManagementSystem.domain.entities.CommentEntity;
import com.example.taskManagementSystem.mappers.Mapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentMapperImpl implements Mapper<CommentEntity, CommentDto> {

    private final ModelMapper modelMapper;

    @Override
    public CommentDto mapToDto(CommentEntity commentEntity) {
        return modelMapper.map(commentEntity, CommentDto.class);
    }

    @Override
    public CommentEntity mapFromDto(CommentDto commentDto) {
        return modelMapper.map(commentDto, CommentEntity.class);
    }
}
