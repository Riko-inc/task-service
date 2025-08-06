package com.example.taskManagementSystem.domain.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(schema = "task_service", name = "comments")
public class CommentEntity {

    @Id
    @SequenceGenerator(name = "comments_seq", sequenceName = "comments_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long commentId;

    // TODO: make cascade operations
    @JoinColumn(name = "author_id")
    private Long authorId;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "parent_task_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private TaskEntity task;

    @Column(updatable = false, nullable = false)
    @CreatedDate
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm", iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime createdDate;
}
