package com.example.demo.service;

import com.example.demo.model.Exam;
import com.example.demo.model.UserExam;
import com.example.demo.reponse.StatisticalResponse;
import com.example.demo.request.UserExamRequest;

import java.util.List;

public interface UserExamService {

    UserExam save(UserExamRequest userExamRequest,Long userId) throws Exception;

    Long caculateCorrectAnswerOfExam(Exam exam, List<String> answers);

    List<StatisticalResponse> getAllUserExamOfEachUserHasBestScore();

    List<StatisticalResponse> getAllUserExamOfEachUserHasBestScoreByName(String examName);

    List<StatisticalResponse> getAllUserExamOfEachUserHasBestScoreByDate(String date);

    List<StatisticalResponse> getAllUserExamOfEachUserHasBestScoreByNameAndDate(String examName, String date);



}
