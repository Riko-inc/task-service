package com.example.taskManagementSystem.domain.events;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeName("stringEvent")
public class StringEvent {
    private EventType eventType;
    private String payload;
    private Instant occurredAt = Instant.now();
}
