package com.example.demo.request;

import lombok.Data;

import java.util.List;

@Data
public class UserExamRequest {

    private Long examId;

    private List<String> answer;
}
