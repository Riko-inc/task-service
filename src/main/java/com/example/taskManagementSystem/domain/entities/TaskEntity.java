package com.example.taskManagementSystem.domain.entities;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(schema = "task-service", name = "tasks")
public class TaskEntity {
    public enum Status { NEW, IN_PROGRESS, COMPLETE }
    public enum Priority { LOW, MEDIUM, HIGH, DEFAULT }

    @Id
    @SequenceGenerator(name = "tasks_seq", sequenceName = "tasks_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long taskId;

    @Column(nullable = false)
    private String title;

    private String description;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDateTime dueTo;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Status status= Status.NEW;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private Priority priority = Priority.DEFAULT;

    @Column(name = "created_by_user_id")
    private Long createdByUserId;

    @OneToMany(mappedBy = "task", cascade = CascadeType.PERSIST, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    @Builder.Default
    private List<CommentEntity> comments = new ArrayList<>();

    @Column(name = "assigned_to_user_id")
    private Long assignedUserId;

    @Column(nullable = false)
    @CreatedDate
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm", iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime createdDate;
}
