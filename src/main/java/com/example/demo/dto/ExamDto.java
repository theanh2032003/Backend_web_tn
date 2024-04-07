package com.example.demo.dto;

import com.example.demo.model.Exam;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ExamDto {
    private Long id;

    private String name;

    private String description;

    private String type;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    public ExamDto(Exam exam) {
        this.id = exam.getId();
        this.name = exam.getName();
        this.description = exam.getDescription();
        this.type = exam.getType();
        this.startTime = exam.getStartTime();
        this.endTime = exam.getEndTime();
    }
}
