package com.example.demo.service;

import com.example.demo.model.Exam;
import com.example.demo.request.ExamRequest;

import java.util.List;
import java.util.Optional;

public interface ExamService {

    List<Exam> getAllExam();

    Exam saveExam(ExamRequest examRequest);

    Optional<Exam> getExamById(Long examId);

    Exam changeExam(Long examId,ExamRequest examRequest);

    void deleteExamById(Long examId);

    void deleteAllExam();

    List<Exam> seacrchExam(String name);

    List<String> getAllExamName();
}
