package com.example.taskManagementSystem.repositories;
import com.example.taskManagementSystem.domain.entities.TaskRelationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRelationRepository extends JpaRepository<TaskRelationEntity, Long>, JpaSpecificationExecutor<TaskRelationEntity> {
    List<TaskRelationEntity> findAllByFromTaskIdOrToTaskId(long fromTaskId, long toTaskId);
}