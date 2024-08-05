package com.example.taskManagementSystem.controllers;
import com.example.taskManagementSystem.domain.dto.CommentDto;
import com.example.taskManagementSystem.domain.dto.requests.CommentCreateRequest;
import com.example.taskManagementSystem.domain.dto.requests.CommentUpdateRequest;
import com.example.taskManagementSystem.domain.entities.CommentEntity;
import com.example.taskManagementSystem.domain.entities.UserEntity;
import com.example.taskManagementSystem.mappers.Mapper;
import com.example.taskManagementSystem.services.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/comment")
@RequiredArgsConstructor
@Validated
@Tag(name = "Работа с комментариями")
public class CommentController {
    private final CommentService commentService;
    private final Mapper<CommentEntity, CommentDto> commentMapper;

    @Operation(summary = "Создать комментарий к задаче")
    @PostMapping
    public ResponseEntity<CommentDto> createComment(@AuthenticationPrincipal UserEntity user, @RequestBody CommentCreateRequest commentRequest){
        CommentEntity savedCommentEntity = commentService.createComment(user, commentRequest);
        return new ResponseEntity<>(commentMapper.mapToDto(savedCommentEntity), HttpStatus.CREATED);
    }

    @Operation(summary = "Получить все комментарии к задаче по её id")
    @GetMapping(path = "/task/{id}")
    public ResponseEntity<List<CommentDto>> getAllCommentsForTask(@PathVariable Long id) {
        List<CommentEntity> commentEntities = commentService.getAllCommentsByTaskId(id);
        return new ResponseEntity<>(commentEntities.stream().map(commentMapper::mapToDto).toList(), HttpStatus.OK);
    }

    @Operation(summary = "Получить комментарий по его id")
    @GetMapping(path = "/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable Long id) {
        CommentEntity savedCommentEntity = commentService.getCommentById(id);
        return new ResponseEntity<>(commentMapper.mapToDto(savedCommentEntity), HttpStatus.OK);

    }

    @Operation(summary = "Обновить изменить текст комментария")
    @PutMapping
    @PreAuthorize("@AccessService.canChangeComment(principal, #commentUpdateRequest.getCommentId())")
    public ResponseEntity<CommentDto> updateComment(@RequestBody CommentUpdateRequest commentUpdateRequest) {
        CommentEntity commentEntity = commentService.updateComment(commentUpdateRequest);
        return new ResponseEntity<>(commentMapper.mapToDto(commentEntity), HttpStatus.OK);
    }

    @Operation(summary = "Удалить комментарий")
    @DeleteMapping(path = "/{id}")
    @PreAuthorize("@AccessService.canChangeComment(principal, #id)")
    public ResponseEntity<HttpStatus> deleteComment(@PathVariable long id) {
        commentService.deleteCommentById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
