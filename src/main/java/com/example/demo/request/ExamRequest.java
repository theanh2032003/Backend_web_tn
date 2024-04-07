package com.example.demo.request;

import com.example.demo.model.Question;
import com.example.demo.model.UserExam;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ExamRequest {
    private String name;

    private String description;

    private String type;

    private List<QuestionRequest> questions;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

}
