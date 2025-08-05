package com.example.taskManagementSystem.domain.enums;

import lombok.Getter;

@Getter
public enum EventType {
    USER_CREATED("user.created"),
    USER_DELETED("user.deleted"),
    USER_UPDATED("user.updated"),

    TASK_CREATED("task.created"),
    TASK_UPDATED("task.updated"),
    TASK_DELETED("task.deleted"),

    SPACE_CREATED("space.created"),
    SPACE_UPDATED("space.updated"),
    SPACE_DELETED("space.deleted");

    private final String code;

    EventType(String code) {
        this.code = code;
    }

}
