//package com.example.taskManagementSystem.controllers;
//
//import static org.mockito.ArgumentMatchers.*;
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//import com.example.taskManagementSystem.domain.dto.requests.TaskCreateRequest;
//import com.example.taskManagementSystem.domain.dto.responses.TaskResponse;
//import com.example.taskManagementSystem.domain.entities.TaskEntity;
//import com.example.taskManagementSystem.mappers.Mapper;
//import com.example.taskManagementSystem.services.TaskService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.web.servlet.MockMvc;
//
//@WebMvcTest(TaskController.class)
//class TaskControllerIntegrationTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @MockBean
//    private TaskService taskService;
//
//    @MockBean
//    private Mapper<TaskEntity, TaskResponse> taskMapper;
//
//    @Test
//    @WithMockUser
//    void createTask_ValidRequest_ReturnsCreated() throws Exception {
//        // Arrange
//        TaskCreateRequest request = new TaskCreateRequest();
//        request.setTitle("New Task");
//
//        TaskEntity entity = new TaskEntity();
//        entity.setTaskId(1L);
//
//        TaskResponse response = new TaskResponse();
//        response.setTaskId(1L);
//
//        when(taskService.createTask(any(), any())).thenReturn(entity);
//        when(taskMapper.mapToDto(any())).thenReturn(response);
//
//        // Act & Assert
//        mockMvc.perform(post("/api/v1/task")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.taskId").value(1L));
//    }
//
//    @Test
//    @WithMockUser
//    void getTaskById_ValidId_ReturnsOk() throws Exception {
//        // Arrange
//        TaskResponse response = new TaskResponse();
//        response.setTaskId(1L);
//
//        when(taskService.getTaskById(1L)).thenReturn(new TaskEntity());
//        when(taskMapper.mapToDto(any())).thenReturn(response);
//
//        // Act & Assert
//        mockMvc.perform(get("/api/v1/task/1"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.taskId").value(1L));
//    }
//
//    @Test
//    @WithMockUser
//    void deleteTask_ValidId_ReturnsOk() throws Exception {
//        // Act & Assert
//        mockMvc.perform(delete("/api/v1/task/1"))
//                .andExpect(status().isOk());
//
//        verify(taskService, times(1)).deleteTaskById(1L);
//    }
//}