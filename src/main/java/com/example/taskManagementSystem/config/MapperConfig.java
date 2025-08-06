package com.example.taskManagementSystem.config;

import com.example.taskManagementSystem.domain.dto.CommentDto;
import com.example.taskManagementSystem.domain.entities.CommentEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setAmbiguityIgnored(true);
        modelMapper.typeMap(CommentEntity.class, CommentDto.class)
                .addMapping(src -> src.getTask().getTaskId(), CommentDto::setTaskId);
        return modelMapper;
    }
}
