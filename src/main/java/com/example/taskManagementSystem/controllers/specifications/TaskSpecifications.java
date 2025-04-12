package com.example.taskManagementSystem.controllers.specifications;
import org.springframework.data.jpa.domain.Specification;
import com.example.taskManagementSystem.domain.entities.TaskEntity;

public class TaskSpecifications {
    public static Specification<TaskEntity> hasStatuses(TaskEntity.Status[] statuses) {
        return (root, query, cb) ->
                statuses == null || statuses.length == 0
                        ? cb.conjunction()
                        : root.get("status").in((Object[]) statuses);
    }

    public static Specification<TaskEntity> hasPriorities(TaskEntity.Priority[] priorities) {
        return (root, query, cb) ->
                priorities == null || priorities.length == 0
                        ? cb.conjunction()
                        : root.get("priority").in((Object[]) priorities);
    }

    public static Specification<TaskEntity> ownedOrAssignedToUser(long userId) {
        return (root, query, cb) -> cb.or(
                cb.equal(root.get("createdByUserId"), userId),
                cb.equal(root.get("assignedUserId"), userId)
        );
    }
}