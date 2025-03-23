package com.example.taskManagementSystem.mappers.impl;

import com.example.taskManagementSystem.domain.dto.responses.UserDetailResponse;
import com.example.taskManagementSystem.mappers.Mapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDetailMapperImpl implements Mapper<UserDetails, UserDetailResponse> {

    private final ModelMapper modelMapper;

    @Override
    public UserDetails mapFromDto(UserDetailResponse userDetailResponse) {
        return modelMapper.map(userDetailResponse, UserDetails.class);
    }

    @Override
    public UserDetailResponse mapToDto(UserDetails a) {
        return modelMapper.map(a, UserDetailResponse.class);
    }
}
