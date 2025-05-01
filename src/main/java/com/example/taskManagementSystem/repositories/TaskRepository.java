package com.example.taskManagementSystem.repositories;
import com.example.taskManagementSystem.domain.entities.TaskEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long>, JpaSpecificationExecutor<TaskEntity> {
    void deleteTaskByTaskId(long id);
    void deleteAllByCreatedByUserId(Long id);
}