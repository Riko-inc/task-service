package com.example.taskManagementSystem.controllers.specifications;
import org.springframework.data.jpa.domain.Specification;
import com.example.taskManagementSystem.domain.entities.TaskEntity;

public class TaskSpecifications {
    public static Specification<TaskEntity> hasStatus(TaskEntity.Status status) {
        return (root, query, builder) ->
                builder.equal(root.get("status"), status.toString());
    }

    public static Specification<TaskEntity> hasPriority(TaskEntity.Priority priority) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("priority"), priority.toString());
    }

    public static Specification<TaskEntity> ownedOrAssignedToUser(long userId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.or(
                        criteriaBuilder.equal(root.get("createdByUserId"), userId),
                        criteriaBuilder.equal(root.get("assignedUserId"), userId)
                );
    }
}
