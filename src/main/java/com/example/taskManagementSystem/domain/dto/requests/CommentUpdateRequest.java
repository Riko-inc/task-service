package com.example.taskManagementSystem.domain.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Запрос на изменение комментария")
public class CommentUpdateRequest {
    @Schema(description = "Id комментария", example = "12")
    @NotNull
    @NotBlank(message = "Id комментария не может быть пустым")
    private long commentId;

    @Schema(type = "string", description = "Текст комментария", example = "Отличная работа!")
    @NotNull
    @NotBlank(message = "Текст комментария к задаче не может быть пустым")
    private String content;
}
