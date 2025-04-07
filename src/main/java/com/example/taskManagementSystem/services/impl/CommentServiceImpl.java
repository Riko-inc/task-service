package com.example.taskManagementSystem.services.impl;

import com.example.taskManagementSystem.domain.dto.requests.CommentCreateRequest;
import com.example.taskManagementSystem.domain.dto.requests.CommentUpdateRequest;
import com.example.taskManagementSystem.domain.entities.CommentEntity;
import com.example.taskManagementSystem.domain.entities.TaskEntity;
import com.example.taskManagementSystem.domain.entities.UserEntity;
import com.example.taskManagementSystem.exceptions.EntityNotFoundException;
import com.example.taskManagementSystem.repositories.CommentRepository;
import com.example.taskManagementSystem.repositories.TaskRepository;
import com.example.taskManagementSystem.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Contains logic for operations with Comment Entity
 */
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final TaskRepository taskRepository;
    private final CommentRepository commentRepository;

    //TODO: implement mapper
    @Override
    @Transactional
    public CommentEntity createComment(UserEntity user, CommentCreateRequest commentRequest) {
        TaskEntity taskEntity = taskRepository.findById(commentRequest.getTaskId())
                .orElseThrow(() -> new EntityNotFoundException("Задача " + commentRequest.getTaskId() + " не найдена"));
        CommentEntity comment = CommentEntity.builder()
                .task(taskEntity)
                .content(commentRequest.getContent())
                .author(user)
                .createdDate(LocalDateTime.now())
                .build();
        return commentRepository.save(comment);
    }

    @Override
    @Transactional
    public CommentEntity updateComment(CommentUpdateRequest commentUpdateRequest) {
        CommentEntity savedComment = commentRepository.findById(commentUpdateRequest.getCommentId())
                .orElseThrow(() -> new EntityNotFoundException("Комментарий " + commentUpdateRequest.getCommentId() + " не найден"));
        savedComment.setContent(commentUpdateRequest.getContent());
        commentRepository.saveAndFlush(savedComment);
        return savedComment;
    }

    @Override
    @Transactional
    public CommentEntity getCommentById(long id) {
        return commentRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("Комментарий " + id + " не найден"));
    }

    @Override
    @Transactional
    public List<CommentEntity> getAllCommentsByTaskId(long id) {
        TaskEntity task = taskRepository.findById(id).orElseThrow();
        return commentRepository.findAllByTask(task);
    }

    @Override
    @Transactional
    public void deleteCommentById(long id) {
        commentRepository.deleteCommentEntityByCommentId(id);
    }
}
