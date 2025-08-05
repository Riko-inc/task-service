package com.example.taskManagementSystem.domain.events;

import com.example.taskManagementSystem.domain.enums.EventType;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;

@Data
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
public class GenericEventMessage<T> implements Serializable {
    private String type;
    private EventType eventType;
    private T payload;
    private Instant occurredAt = Instant.now();

    @JsonCreator
    public GenericEventMessage(
            @JsonProperty("type") String type,
            @JsonProperty("eventType") EventType eventType,
            @JsonProperty("payload") T payload,
            @JsonProperty("occurredAt") Instant occurredAt) {
        this.type = type != null ? type : "genericEvent";
        this.eventType = eventType;
        this.payload = payload;
        this.occurredAt = occurredAt != null ? occurredAt : Instant.now();
    }

    public GenericEventMessage(EventType eventType, T payload) {
        this.type = "genericEvent";
        this.eventType = eventType;
        this.payload = payload;
    }
}
