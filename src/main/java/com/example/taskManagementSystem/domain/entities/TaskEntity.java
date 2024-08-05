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
@Table(name = "tasks")
public class TaskEntity {
    public enum Status { NEW, IN_PROGRESS, COMPLETE }
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
    private LocalDateTime dueTo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.NEW;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Priority priority = Priority.LOW;

    @ManyToOne
    @JoinColumn(name = "created_by_user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserEntity createdByUser;

    @OneToMany(mappedBy = "task", cascade = CascadeType.PERSIST, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    @ToString.Exclude
    private List<CommentEntity> comments = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "assigned_to_user_id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private UserEntity assignedUser;

    @Column(nullable = false)
    @CreatedDate
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm", iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime createdDate;
}
