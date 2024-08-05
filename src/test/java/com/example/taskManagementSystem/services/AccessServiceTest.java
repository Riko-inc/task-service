package com.example.taskManagementSystem.services;

import com.example.taskManagementSystem.domain.entities.CommentEntity;
import com.example.taskManagementSystem.domain.entities.TaskEntity;
import com.example.taskManagementSystem.domain.entities.UserEntity;
import com.example.taskManagementSystem.repositories.CommentRepository;
import com.example.taskManagementSystem.repositories.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
        taskEntity.setCreatedByUser(userEntity);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(taskEntity));

        assertTrue(accessService.canChangeTask(userEntity, 1L));
        assertFalse(accessService.canChangeTask(userEntity, 2L));
    }

    @Test
    public void testCanChangeStatus() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(1L);

        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setCreatedByUser(userEntity);
        taskEntity.setAssignedUser(userEntity);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(taskEntity));

        assertTrue(accessService.canChangeStatus(userEntity, 1L));
        assertFalse(accessService.canChangeStatus(userEntity, 2L));
    }

    @Test
    public void testCanChangeComment() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(1L);

        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setAuthor(userEntity);

        when(commentRepository.findById(1L)).thenReturn(Optional.of(commentEntity));

        assertTrue(accessService.canChangeComment(userEntity, 1L));
        assertFalse(accessService.canChangeComment(userEntity, 2L));
    }
}