package com.example.taskManagementSystem.domain.entities;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "tasks")
public class TaskEntity {
    public enum Status { NEW, OVERDUE, COMPLETED, DELETED }
    public enum Priority { LOW, MEDIUM, HIGH }

    @Id
    @SequenceGenerator(name = "tasks_seq", sequenceName = "tasks_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long taskId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDateTime endDeadline;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.NEW;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Priority priority = Priority.LOW;

    @ManyToOne
    @JoinColumn(name = "created_by_user_id", nullable = false)
    private UserEntity createdByUser;

    @OneToOne
    @JoinColumn(name = "assigned_to_user_id")
    private UserEntity assignedUser;

    @Column(nullable = false)
    @CreatedDate
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm", iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdDate;
}
