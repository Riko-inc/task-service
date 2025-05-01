package com.example.taskManagementSystem.domain.events;

import lombok.Getter;

@Getter
public enum EventType {
    USER_CREATED("user.created"),
    USER_DELETED("user.deleted"),
    TASK_CREATED("task.created"),
    TASK_UPDATED("task.updated"),
    TASK_DELETED("task.deleted");

    private final String code;

    EventType(String code) {
        this.code = code;
    }

}
