package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "exam")
public class Exam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private String type;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @OneToMany(mappedBy = "exam")
    private List<Question> questions;

    @OneToMany(mappedBy = "exam")
    private List<UserExam> userExams;

    public Exam(String name, String description, String type, LocalDateTime start, LocalDateTime end, List<Question> questions) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.startTime = start;
        this.endTime = end;
        this.questions = questions;
    }
}
