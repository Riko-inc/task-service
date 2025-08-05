package com.example.taskManagementSystem.services;

import com.example.taskManagementSystem.domain.entities.CommentEntity;
import com.example.taskManagementSystem.domain.entities.TaskEntity;
import com.example.taskManagementSystem.domain.entities.UserEntity;
import com.example.taskManagementSystem.exceptions.EntityNotFoundException;
import com.example.taskManagementSystem.repositories.CommentRepository;
import com.example.taskManagementSystem.repositories.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccessServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private CommentRepository commentRepository;

    private AccessService accessService;

    @BeforeEach
    public void setUp() {
        accessService = new AccessService(taskRepository, commentRepository);
    }

    @Test
    public void testCanChangeTask() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(1L);

        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setCreatedByUserId(userEntity.getUserId());
        taskEntity.setCreatedDateTime(LocalDateTime.now());

        when(taskRepository.findById(1L)).thenReturn(Optional.of(taskEntity));

        assertTrue(accessService.canChangeTask(userEntity, 1L));
        assertThrows(EntityNotFoundException.class, () -> accessService.canChangeTask(userEntity, 2L));
    }

    @Test
    public void testCanChangeStatus() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(1L);

        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setCreatedByUserId(userEntity.getUserId());
        taskEntity.setAssignedUserId(userEntity.getUserId());
        taskEntity.setCreatedDateTime(LocalDateTime.now());

        when(taskRepository.findById(1L)).thenReturn(Optional.of(taskEntity));

        assertTrue(accessService.canChangeStatus(userEntity, 1L));
        assertThrows(EntityNotFoundException.class, () -> accessService.canChangeTask(userEntity, 2L));
    }

    @Test
    public void testCanChangeComment() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(1L);

        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setAuthorId(userEntity.getUserId());
        commentEntity.setCreatedDate(LocalDateTime.now());

        when(commentRepository.findById(1L)).thenReturn(Optional.of(commentEntity));

        assertTrue(accessService.canChangeComment(userEntity, 1L));
        assertThrows(EntityNotFoundException.class, () -> accessService.canChangeComment(userEntity, 2L));
    }
}