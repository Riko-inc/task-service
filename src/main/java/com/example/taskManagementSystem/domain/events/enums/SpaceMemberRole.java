package com.example.taskManagementSystem.domain.events.enums;

import lombok.Getter;

@Getter
public enum SpaceMemberRole {
    OWNER(0), MEMBER(1), READER(2);
    private final int order;

    SpaceMemberRole(int order) {
        this.order = order;
    }
}