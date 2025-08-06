package com.example.taskManagementSystem.services.impl;

import com.example.taskManagementSystem.domain.dto.requests.TaskRelationCreateRequest;
import com.example.taskManagementSystem.domain.dto.requests.TaskRelationUpdateRequest;
import com.example.taskManagementSystem.domain.dto.responses.TaskRelationResponse;
import com.example.taskManagementSystem.domain.entities.TaskEntity;
import com.example.taskManagementSystem.domain.entities.TaskRelationEntity;
import com.example.taskManagementSystem.domain.enums.RelationType;
import com.example.taskManagementSystem.exceptions.EntityNotFoundException;
import com.example.taskManagementSystem.exceptions.InvalidRequestParameterException;
import com.example.taskManagementSystem.mappers.impl.TaskRelationCreateMapperImpl;
import com.example.taskManagementSystem.mappers.impl.TaskRelationResponseMapperImpl;
import com.example.taskManagementSystem.repositories.TaskRelationRepository;
import com.example.taskManagementSystem.repositories.TaskRepository;
import com.example.taskManagementSystem.services.TaskRelationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TaskRelationServiceImpl implements TaskRelationService {
    private final TaskRelationRepository taskRelationRepository;
    private final TaskRepository taskRepository;
    private final TaskRelationResponseMapperImpl taskRelationResponseMapper;
    private final TaskRelationCreateMapperImpl taskRelationCreateMapper;

    private boolean isSameTask(Long fromTaskId, Long toTaskId) {
        return Objects.equals(fromTaskId, toTaskId);
    }

    private boolean tasksNotExist(Long fromTaskId, Long toTaskId) {
        return !taskRepository.existsById(fromTaskId) || !taskRepository.existsById(toTaskId);
    }

    @Override
    public List<TaskRelationResponse> getTaskRelations(Long taskId) {
        List<TaskRelationEntity> relations = taskRelationRepository.findAllByFromTaskId(taskId);
        return relations.stream().map(taskRelationResponseMapper::mapToDto).toList();
    }

    public TaskRelationResponse createTaskRelation(TaskRelationCreateRequest request) {
        if (isSameTask(request.getToTaskId(), request.getFromTaskId())) {
            throw new InvalidRequestParameterException("fromTaskId cannot be equal to toTaskId");
        }

        if (tasksNotExist(request.getFromTaskId(), request.getToTaskId())) {
            throw new EntityNotFoundException("Some of the given tasks does not exist");
        }

        TaskRelationEntity taskRelationEntityFrom = taskRelationCreateMapper.mapFromDto(request);

        request.setFromTaskId(taskRelationEntityFrom.getToTaskId());
        request.setToTaskId(taskRelationEntityFrom.getFromTaskId());
        request.setRelation(RelationType.getOpposite(request.getRelation()));
        TaskRelationEntity taskRelationEntityTo = taskRelationCreateMapper.mapFromDto(request);
        taskRelationRepository.save(taskRelationEntityTo);

        return taskRelationResponseMapper.mapToDto(taskRelationRepository.save(taskRelationEntityFrom));
    }

    public TaskRelationResponse updateTaskRelation(TaskRelationUpdateRequest request) {
        TaskRelationEntity existingRelation = taskRelationRepository.findById(request.getRelationId())
                .orElseThrow(() -> new EntityNotFoundException("Task relation not found with id: " + request.getRelationId()));

        if (isSameTask(request.getToTaskId(), request.getFromTaskId())) {
            throw new InvalidRequestParameterException("fromTaskId cannot be equal to toTaskId");
        }

        TaskEntity fromTask = taskRepository
                .findById(request.getFromTaskId())
                .orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + request.getFromTaskId()));

        TaskEntity toTask = taskRepository
                .findById(request.getToTaskId())
                .orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + request.getToTaskId()));

        existingRelation.setRelation(request.getRelation());
        existingRelation.setFromTaskId(fromTask.getTaskId());
        existingRelation.setToTaskId(toTask.getTaskId());

        return taskRelationResponseMapper.mapToDto(taskRelationRepository.save(existingRelation));
    }

    @Override
    public void deleteTaskRelation(Long taskRelationId) {

    }
}
