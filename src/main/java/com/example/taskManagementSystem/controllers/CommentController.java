package com.example.taskManagementSystem.controllers;
import com.example.taskManagementSystem.domain.dto.CommentDto;
import com.example.taskManagementSystem.domain.dto.requests.CommentCreateRequest;
import com.example.taskManagementSystem.domain.dto.requests.CommentUpdateRequest;
import com.example.taskManagementSystem.domain.entities.CommentEntity;
import com.example.taskManagementSystem.domain.entities.UserEntity;
import com.example.taskManagementSystem.mappers.Mapper;
import com.example.taskManagementSystem.services.CommentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/comment")
@RequiredArgsConstructor
@Tag(name = "Работа с комментариями")
public class CommentController {
    private final CommentService commentService;
    private final Mapper<CommentEntity, CommentDto> commentMapper;

    @PostMapping
    public ResponseEntity<CommentDto> createComment(@AuthenticationPrincipal UserEntity user, @RequestBody CommentCreateRequest commentRequest){
        CommentEntity savedCommentEntity = commentService.createComment(user, commentRequest);
        return new ResponseEntity<>(commentMapper.mapToDto(savedCommentEntity), HttpStatus.CREATED);
    }

    @GetMapping(path = "/task/{id}")
    public ResponseEntity<List<CommentDto>> getAllCommentsForTask(@PathVariable Long id) {
        List<CommentEntity> commentEntities = commentService.getAllCommentsByTaskId(id);
        return new ResponseEntity<>(commentEntities.stream().map(commentMapper::mapToDto).toList(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable Long id) {
        Optional<CommentEntity> savedCommentEntity = commentService.getCommentById(id);
        return savedCommentEntity.map(commentEntity -> new ResponseEntity<>(commentMapper.mapToDto(commentEntity), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping
    @PreAuthorize("@AccessService.canChangeComment(principal, #commentUpdateRequest.getCommentId())")
    public ResponseEntity<CommentDto> updateComment(@RequestBody CommentUpdateRequest commentUpdateRequest) {
        Optional<CommentEntity> result = commentService.updateComment(commentUpdateRequest);
        return result.map(commentEntity -> new ResponseEntity<>(commentMapper.mapToDto(commentEntity), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping(path = "/{id}")
    @PreAuthorize("@AccessService.canChangeComment(principal, #id)")
    public ResponseEntity<HttpStatus> deleteComment(@PathVariable long id) {
        commentService.deleteCommentById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
