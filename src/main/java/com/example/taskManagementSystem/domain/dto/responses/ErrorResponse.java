package com.example.taskManagementSystem.domain.dto.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.Map;
import java.util.UUID;



@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Ошибка выполнения")
public class ErrorResponse {
    @Schema(description = "Id ошибки", example = "UUID")
    private UUID id;
    @Schema(description = "Сообщение ошибки", example = "Упс, что-то сломалось...")
    private String message;
    private Map<String, Object> params;
}
