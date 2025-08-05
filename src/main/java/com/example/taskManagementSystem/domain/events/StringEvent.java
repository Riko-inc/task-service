package com.example.taskManagementSystem.domain.events;

import com.example.taskManagementSystem.domain.enums.EventType;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeName("stringEvent")
public class StringEvent {
    private EventType eventType;
    private String payload;
    private Instant occurredAt = Instant.now();
}
