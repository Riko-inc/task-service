package com.example.taskManagementSystem.domain.dto.requests;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Запрос на создание нового комментария")
public class CommentCreateRequest {
    @Schema(description = "Id задачи", example = "12")
    @NotNull(message = "Id задачи не может быть пустым")
    private long taskId;

    @Schema(type = "string", description = "Текст комментария", example = "Отличная работа!")
    @NotNull
    @NotBlank(message = "Текст комментария к задаче не может быть пустым")
    private String content;

}
