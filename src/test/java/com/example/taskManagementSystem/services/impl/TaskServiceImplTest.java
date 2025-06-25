//package com.example.taskManagementSystem.services.impl;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//import java.time.LocalDateTime;
//import java.util.*;
//
//import com.example.taskManagementSystem.domain.dto.requests.TaskCreateRequest;
//import com.example.taskManagementSystem.domain.dto.requests.TaskGetAllRequest;
//import com.example.taskManagementSystem.domain.dto.requests.TaskUpdateRequest;
//import com.example.taskManagementSystem.domain.entities.TaskEntity;
//import com.example.taskManagementSystem.domain.entities.UserEntity;
//import com.example.taskManagementSystem.exceptions.EntityNotFoundException;
//import com.example.taskManagementSystem.exceptions.InvalidRequestParameterException;
//import com.example.taskManagementSystem.repositories.TaskRepository;
//import com.example.taskManagementSystem.services.AuthClientService;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.data.domain.*;
//import org.springframework.data.jpa.domain.Specification;
//
//@ExtendWith(MockitoExtension.class)
//class TaskServiceImplTest {
//
//    @Mock
//    private TaskRepository taskRepository;
//
//    @Mock
//    private AuthClientService authService;
//
//    @InjectMocks
//    private TaskServiceImpl taskService;
//
//    @Test
//    void createTask_Success() {
//        UserEntity user = new UserEntity();
//        user.setUserId(1L);
//
//        TaskCreateRequest request = new TaskCreateRequest();
//        request.setTitle("Test Task");
//        request.setDescription("Test Description");
//        request.setPriority(TaskEntity.Priority.HIGH);
//        request.setStatus(TaskEntity.Status.NEW);
//        request.setAssignedToUserId(2L);
//
//        TaskEntity savedTask = TaskEntity.builder()
//                .taskId(1L)
//                .title("Test Task")
//                .position(1.0)
//                .build();
//
//        when(authService.checkUserIdExists(2L)).thenReturn(true);
//        when(taskRepository.save(any())).thenReturn(savedTask);
//        TaskEntity result = taskService.createTask(user, request);
//
//        assertNotNull(result);
//        assertEquals(1L, result.getTaskId());
//        assertEquals("Test Task", result.getTitle());
//        assertEquals(1.0, result.getPosition());
//        verify(taskRepository, times(2)).save(any());
//    }
//
//    @Test
//    void createTask_InvalidPriority_ThrowsException() {
//        UserEntity user = new UserEntity();
//        TaskCreateRequest request = new TaskCreateRequest();
//        request.setPriority(null);
//
//        assertThrows(InvalidRequestParameterException.class,
//                () -> taskService.createTask(user, request));
//    }
//
//    @Test
//    void updateTask_Success() {
//        TaskUpdateRequest request = new TaskUpdateRequest();
//        request.setTaskId(1L);
//        request.setTitle("Updated Title");
//        request.setPriority(TaskEntity.Priority.MEDIUM);
//        request.setStatus(TaskEntity.Status.IN_PROGRESS);
//
//        TaskEntity existingTask = TaskEntity.builder()
//                .taskId(1L)
//                .title("Original Title")
//                .priority(TaskEntity.Priority.LOW)
//                .status(TaskEntity.Status.NEW)
//                .build();
//
//        when(taskRepository.findById(1L)).thenReturn(Optional.of(existingTask));
//        when(authService.checkUserIdExists(any())).thenReturn(true);
//        when(taskRepository.saveAndFlush(any())).thenReturn(existingTask);
//
//        TaskEntity result = taskService.updateTask(request);
//
//        assertEquals("Updated Title", result.getTitle());
//        assertEquals(TaskEntity.Priority.MEDIUM, result.getPriority());
//        assertEquals(TaskEntity.Status.IN_PROGRESS, result.getStatus());
//    }
//
//    @Test
//    void getTaskById_NotFound_ThrowsException() {
//        when(taskRepository.findById(999L)).thenReturn(Optional.empty());
//
//        assertThrows(EntityNotFoundException.class,
//                () -> taskService.getTaskById(999L));
//    }
//
//    @Test
//    void getAllTasksByUserId_WithFilters() {
//        TaskGetAllRequest request = new TaskGetAllRequest();
//        request.setPage(0);
//        request.setSize(10);
//        request.setSort("priority,desc");
//        request.setStatus(List.of(TaskEntity.Status.NEW));
//        request.setPriority(List.of(TaskEntity.Priority.HIGH));
//        request.setCreatedByUserId(List.of(1L));
//
//        Page<TaskEntity> page = new PageImpl<>(Collections.emptyList());
//        when(taskRepository.findAll(any(Specification.class), any(Pageable.class)))
//                .thenReturn(page);
//
//        List<TaskEntity> result = taskService.getAllTasksByUserId(1L, request);
//
//        assertTrue(result.isEmpty());
//    }
//
//    @Test
//    void deleteTaskById_Success() {
//        doNothing().when(taskRepository).deleteTaskByTaskId(1L);
//        taskService.deleteTaskById(1L);
//        verify(taskRepository, times(1)).deleteTaskByTaskId(1L);
//    }
//}