package com.example.taskManagementSystem.domain.enums;
import lombok.Getter;

@Getter
public enum TaskPriority {
    DEFAULT(0), LOW(1), MEDIUM(2), HIGH(3);
    private final int order;
    TaskPriority(int order) {
        this.order = order;
    }
}
