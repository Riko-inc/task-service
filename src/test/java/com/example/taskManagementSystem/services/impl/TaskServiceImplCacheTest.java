//package com.example.taskManagementSystem.services.impl;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//import java.util.List;
//import java.util.Optional;
//
//import com.example.taskManagementSystem.domain.dto.requests.TaskCreateRequest;
//import com.example.taskManagementSystem.domain.entities.TaskEntity;
//import com.example.taskManagementSystem.domain.entities.UserEntity;
//import com.example.taskManagementSystem.repositories.TaskRepository;
//import com.example.taskManagementSystem.services.AuthClientService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.cache.CacheManager;
//import org.springframework.cache.annotation.EnableCaching;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
//
//@SpringJUnitConfig
//@SpringBootTest(classes = {TaskServiceImplCacheTest.CachingConfig.class, TaskServiceImpl.class})
//class TaskServiceImplCacheTest {
//
//    @Configuration
//    @EnableCaching
//    static class CachingConfig {}
//
//    @Autowired
//    private TaskServiceImpl taskService;
//
//    @Autowired
//    private CacheManager cacheManager;
//
//    @MockBean
//    private TaskRepository taskRepository;
//
//    @MockBean
//    private AuthClientService authService;
//
//    @Test
//    void getTaskById_CachingBehavior() {
//        // Arrange
//        TaskEntity task = TaskEntity.builder().taskId(1L).title("Cached Task").build();
//        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
//
//        // Act - First call (should cache)
//        TaskEntity result1 = taskService.getTaskById(1L);
//
//        // Act - Second call (should be from cache)
//        TaskEntity result2 = taskService.getTaskById(1L);
//
//        // Assert
//        assertEquals("Cached Task", result1.getTitle());
//        assertEquals("Cached Task", result2.getTitle());
//        verify(taskRepository, times(1)).findById(1L); // Only 1 DB call
//
//        // Verify cache content
//        assertNotNull(cacheManager.getCache("tasks").get(1L));
//    }
//
//    @Test
//    void createTask_CacheEviction() {
//        // Arrange
//        when(authService.checkUserIdExists(any())).thenReturn(true);
//        when(taskRepository.save(any())).thenAnswer(inv -> {
//            TaskEntity t = inv.getArgument(0);
//            t.setTaskId(2L);
//            return t;
//        });
//
//        // Pre-cache some data
//        cacheManager.getCache("tasks").put(1L, new TaskEntity());
//        cacheManager.getCache("taskLists").put("key", List.of(new TaskEntity()));
//
//        // Act
//        taskService.createTask(new UserEntity(), new TaskCreateRequest());
//
//        // Assert
//        assertNull(cacheManager.getCache("tasks").get(1L)); // Individual cache cleared
//        assertNull(cacheManager.getCache("taskLists").get("key")); // List cache cleared
//    }
//}