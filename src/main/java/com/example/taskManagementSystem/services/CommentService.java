package com.example.taskManagementSystem.services;

import com.example.taskManagementSystem.domain.dto.requests.CommentCreateRequest;
import com.example.taskManagementSystem.domain.dto.requests.CommentUpdateRequest;
import com.example.taskManagementSystem.domain.entities.CommentEntity;
import com.example.taskManagementSystem.domain.entities.UserEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {
    CommentEntity createComment(UserEntity user, CommentCreateRequest commentRequest);
    CommentEntity updateComment(CommentUpdateRequest commentUpdateRequest);
    CommentEntity getCommentById(long id);
    List<CommentEntity> getAllCommentsByTaskId(long id);
    void deleteCommentById(long id);
}
