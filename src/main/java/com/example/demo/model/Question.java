package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String question;

    private List<String> options;

    private String correctAnswer;

    @ManyToOne
    @JoinColumn(name = "exam")
    @JsonBackReference
    private Exam exam;

    public Question(String question, List<String> options, String correctAnswer, Exam exam) {
        this.question = question;
        this.options = options;
        this.correctAnswer = correctAnswer;
        this.exam = exam;
    }
}
