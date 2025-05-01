package com.example.taskManagementSystem.services.kafka;

import com.example.taskManagementSystem.domain.entities.TaskEntity;
import com.example.taskManagementSystem.domain.events.StringEvent;
import com.example.taskManagementSystem.domain.events.UserCreatedEvent;
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
public class UserEventsListener {

    private final TaskRepository taskRepository;

//    @KafkaListener(topics = "user-events", groupId = "task-service")
//    public void handleUserDeleted(UserDeletedEvent event) {
//        Long userId = event.getUserId();
//        taskRepository.deleteAllByCreatedByUserId(userId);
//    }

    @KafkaListener(topics = "user-events", groupId = "task-service")
    public void handleUserCreated(StringEvent event) {
        log.info("event type: {} \n payload: {}", event.getEventType(), event.getPayload());
        Long userId = Long.valueOf(event.getPayload());
        TaskEntity firstTask = TaskEntity.builder()
                .createdByUserId(userId)
                .title("Твоя первая задача - всегда помнить про первую задачу бойцовского клуба")
                .priority(TaskEntity.Priority.MEDIUM)
                .status(TaskEntity.Status.COMPLETE)
                .createdDate(LocalDateTime.now())
                .build();

        TaskEntity secondTask = TaskEntity.builder()
                .createdByUserId(userId)
                .title("Вторая задача - всегда помнить про первую задачу!")
                .priority(TaskEntity.Priority.LOW)
                .status(TaskEntity.Status.IN_PROGRESS)
                .createdDate(LocalDateTime.now())
                .build();

        TaskEntity thirdTask = TaskEntity.builder()
                .title("Ну и третья задача - сгоняй Максу за пивом, он вам всё-таки кафку прикрутил")
                .priority(TaskEntity.Priority.HIGH)
                .status(TaskEntity.Status.NEW)
                .createdDate(LocalDateTime.now())
                .createdByUserId(1L)
                .assignedUserId(userId)
                .build();

        taskRepository.saveAll(List.of(firstTask, secondTask, thirdTask));
    }
}

