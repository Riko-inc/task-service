package com.example.taskManagementSystem.mappers.impl;

import com.example.taskManagementSystem.domain.dto.UserDto;
import com.example.taskManagementSystem.domain.entities.UserEntity;
import com.example.taskManagementSystem.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl implements Mapper<UserEntity, UserDto> {

    private final ModelMapper modelMapper;

    public UserMapperImpl(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDto mapToDto(UserEntity userEntity) {
        return modelMapper.map(userEntity, UserDto.class);
    }

    @Override
    public UserEntity mapFromDto(UserDto userDto) {
        return modelMapper.map(userDto, UserEntity.class);
    }
}
