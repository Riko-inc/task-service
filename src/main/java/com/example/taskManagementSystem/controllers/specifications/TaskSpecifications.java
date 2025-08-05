package com.example.taskManagementSystem.controllers.specifications;
import com.example.taskManagementSystem.domain.enums.TaskPriority;
import com.example.taskManagementSystem.domain.enums.TaskStatus;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import com.example.taskManagementSystem.domain.entities.TaskEntity;

import java.time.LocalDateTime;
import java.util.List;

public class TaskSpecifications {
    public static Specification<TaskEntity> hasStatuses(List<TaskStatus> statuses) {
        return (root, query, cb) ->
                statuses == null || statuses.isEmpty()
                        ? cb.conjunction()
                        : root.get("status").in(statuses);
    }

    public static Specification<TaskEntity> hasPriorities(List<TaskPriority> priorities) {
        return (root, query, cb) ->
                priorities == null || priorities.isEmpty()
                        ? cb.conjunction()
                        : root.get("priority").in(priorities);
    }

    public static Specification<TaskEntity> ownedOrAssignedToUser(long userId) {
        return (root, query, cb) -> cb.or(
                cb.equal(root.get("createdByUserId"), userId),
                cb.equal(root.get("assignedUserId"), userId)
        );
    }

    public static Specification<TaskEntity> inSpace(long spaceId) {
        return (root, query, cb) ->
            cb.equal(root.get("spaceId"), spaceId);
    }

    public static Specification<TaskEntity> ownedByUsers(List<Long> userIds) {
        return (root, query, cb) -> {
            if (userIds == null || userIds.isEmpty()) {
                return cb.conjunction(); // или cb.disjunction() в зависимости от логики
            }
            return root.get("createdByUserId").in(userIds);
        };
    }

    public static Specification<TaskEntity> assignedToUsers(List<Long> userIds) {
        return (root, query, cb) -> {
            if (userIds == null || userIds.isEmpty()) {
                return cb.conjunction(); // или cb.disjunction() в зависимости от логики
            }
            return root.get("assignedUserId").in(userIds);
        };
    }

    public static Specification<TaskEntity> inGivenTimePeriod(LocalDateTime start, LocalDateTime end) {
        return (root, query, cb) -> (
                cb.between(root.get("dueTo"), start, end)
        );
    }

    public static Specification<TaskEntity> orderByStatus(Sort.Direction direction) {
        return (root, query, cb) -> {
            CriteriaBuilder.Case<Integer> statusCase = cb.selectCase();
            for (TaskStatus status : TaskStatus.values()) {
                statusCase = statusCase.when(cb.equal(root.get("status"), status.name()), status.getOrder());
            }
            Expression<Integer> statusOrder = statusCase.otherwise(99);

            if (direction == Sort.Direction.ASC) {
                query.orderBy(
                        cb.asc(statusOrder)
                );
            } else {
                query.orderBy(
                        cb.desc(statusOrder)
                );
            }
            return cb.conjunction();
        };
    }

    public static Specification<TaskEntity> orderByPriority(Sort.Direction direction) {
        return (root, query, cb) -> {
            CriteriaBuilder.Case<Integer> priorityCase = cb.selectCase();
            for (TaskPriority priority : TaskPriority.values()) {
                priorityCase = priorityCase.when(cb.equal(root.get("priority"), priority.name()), priority.getOrder());
            }
            Expression<Integer> priorityOrder = priorityCase.otherwise(99);
            if (direction == Sort.Direction.ASC) {
                query.orderBy(
                        cb.asc(priorityOrder)
                );
            } else {
                query.orderBy(
                        cb.desc(priorityOrder)
                );
            }
            return cb.conjunction();
        };
    }

    public static Specification<TaskEntity> orderByPosition() {
        return (root, query, cb) -> {
                query.orderBy(cb.asc(root.get("position")));
            return cb.conjunction();
        };
    }
}