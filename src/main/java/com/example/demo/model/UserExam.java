package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserExam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    @JsonBackReference
    private User user;

    @ManyToOne
    @JoinColumn(name = "exam_id",nullable = false)
    @JsonBackReference
    private Exam exam;

    private List<String> answer;

    private Long numberOfCorrectAnswer;

    private String doAt;

    public UserExam(User user, Exam exam, List<String> answer) {
        this.user = user;
        this.exam = exam;
        this.answer = answer;
        this.numberOfCorrectAnswer = Long.valueOf(0) ;
        this.doAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
