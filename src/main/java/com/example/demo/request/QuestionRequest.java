package com.example.demo.request;

import lombok.Data;

import java.util.List;

@Data
public class QuestionRequest {

    private String question;

    private List<String> options;

    private String correctAnswer;
}
