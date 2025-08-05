package com.example.taskManagementSystem.domain.enums;
import lombok.Getter;

@Getter
public enum TaskStatus {
    NEW(0), IN_PROGRESS(1), COMPLETE(2);
    private final int order;
    TaskStatus(int order) {
        this.order = order;
    }
}