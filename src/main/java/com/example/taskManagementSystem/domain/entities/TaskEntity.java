package com.example.taskManagementSystem.domain.entities;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
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
@Table(schema = "task_service", name = "tasks")
public class TaskEntity {
    @Getter
    public enum Status {
        NEW(0), IN_PROGRESS(1), COMPLETE(2);
        private final int order;
        Status(int order) {
            this.order = order;
        }
    }

    @Getter
    public enum Priority {
        DEFAULT(0), LOW(1), MEDIUM(2), HIGH(3);
        private final int order;
        Priority(int order) {
            this.order = order;
        }
    }

    @Id
    @SequenceGenerator(name = "tasks_seq", sequenceName = "tasks_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long taskId;

    @Column(nullable = false)
    private String title;

    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
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
