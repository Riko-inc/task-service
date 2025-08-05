package com.example.taskManagementSystem.services.kafka;

import com.example.taskManagementSystem.domain.entities.TaskEntity;
import com.example.taskManagementSystem.domain.enums.TaskPriority;
import com.example.taskManagementSystem.domain.enums.TaskStatus;
import com.example.taskManagementSystem.domain.events.GenericEventMessage;
import com.example.taskManagementSystem.domain.events.dto.WorkspaceDto;
import com.example.taskManagementSystem.repositories.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class SpaceEventsListener {

    private final TaskRepository taskRepository;

    @KafkaListener(topics = "space-events", groupId = "task-service")
    public void handleUserCreated(GenericEventMessage<WorkspaceDto> event) {
        log.info("event type: {} \n payload: {}", event.getEventType(), event.getPayload());
        WorkspaceDto dto = event.getPayload();
        TaskEntity firstTask = TaskEntity.builder()
                .createdByUserId(dto.getMembers().getFirst().getSpaceMemberId())
                .title("Твоя первая задача - всегда помнить про первую задачу бойцовского клуба")
                .priority(TaskPriority.MEDIUM)
                .status(TaskStatus.COMPLETE)
                .createdDateTime(LocalDateTime.now())
                .position(1D)
                .build();

        TaskEntity secondTask = TaskEntity.builder()
                .createdByUserId(dto.getMembers().getFirst().getSpaceMemberId())
                .title("Вторая задача - всегда помнить про первую задачу!")
                .priority(TaskPriority.LOW)
                .status(TaskStatus.IN_PROGRESS)
                .createdDateTime(LocalDateTime.now())
                .position(2D)
                .build();

        TaskEntity thirdTask = TaskEntity.builder()
                .createdByUserId(dto.getMembers().getFirst().getSpaceMemberId())
                .title("Ну и третья задача - сгоняй Максу за пивом, он вам всё-таки кафку прикрутил")
                .priority(TaskPriority.HIGH)
                .status(TaskStatus.NEW)
                .createdDateTime(LocalDateTime.now())
                .position(3D)
                .assignedUserId(dto.getMembers().getFirst().getSpaceMemberId())
                .build();

        taskRepository.saveAll(List.of(firstTask, secondTask, thirdTask));
    }
}

