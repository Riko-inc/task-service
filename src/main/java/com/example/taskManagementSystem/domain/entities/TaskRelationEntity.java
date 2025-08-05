package com.example.taskManagementSystem.domain.entities;
import com.example.taskManagementSystem.domain.enums.RelationType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(schema = "task_service", name = "tasks_relations")
public class TaskRelationEntity {
    @Id
    @SequenceGenerator(name = "tasks_relations_seq", sequenceName = "tasks_relations_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long relationId;

    @Enumerated(EnumType.STRING)
    private RelationType relation;

    @Column(name = "from_task_id", nullable = false)
    private long fromTaskId;

    @Column(name = "to_task_id", nullable = false)
    private long toTaskId;
}
