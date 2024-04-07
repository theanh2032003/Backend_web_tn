package com.example.demo.reponse;

import com.example.demo.model.UserExam;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
public class StatisticalResponse {
    private Long userId;

    private String userName;

    private Long score;

    private String examName;

    private String startTime;

    private String endTime;

    private String doAt;

    public StatisticalResponse(UserExam userExam) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.userId = userExam.getUser().getId();
        this.userName = userExam.getUser().getEmail();
        this.score = userExam.getNumberOfCorrectAnswer();
        this.examName = userExam.getExam().getName();
        this.startTime = userExam.getExam().getStartTime().format(formatter);
        this.endTime = userExam.getExam().getEndTime().format(formatter);
        this.doAt = userExam.getDoAt();
    }
}
