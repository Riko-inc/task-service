package com.example.taskManagementSystem.domain.enums;

import lombok.Getter;

import java.util.EnumMap;

@Getter
public enum RelationType {
    PARENT(0), SUBTASK(1),
    BLOCKS(2), BLOCKED_BY(4),
    RELATES_TO(5),
    CLONE_OF(6), CLONED_BY(7);

    private final int relationId;

    private static final EnumMap<RelationType, RelationType> OPPOSITES = new EnumMap<>(RelationType.class);

    static {
        OPPOSITES.put(PARENT, SUBTASK);
        OPPOSITES.put(SUBTASK, PARENT);
        OPPOSITES.put(BLOCKS, BLOCKED_BY);
        OPPOSITES.put(BLOCKED_BY, BLOCKS);
        OPPOSITES.put(CLONE_OF, CLONED_BY);
        OPPOSITES.put(CLONED_BY, CLONE_OF);
        OPPOSITES.put(RELATES_TO, RELATES_TO);
    }

    RelationType(int relationId) {
        this.relationId = relationId;
    }

    public static RelationType getOpposite(RelationType relationType) {
        return OPPOSITES.get(relationType);
    }
}