package com.example.demo.service.impl;

import com.example.demo.model.Exam;
import com.example.demo.model.User;
import com.example.demo.model.UserExam;
import com.example.demo.reponse.StatisticalResponse;
import com.example.demo.repository.ExamRepository;
import com.example.demo.repository.UserExamRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.request.UserExamRequest;
import com.example.demo.service.UserExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserExamServiceImpl implements UserExamService {
    @Autowired
    private UserExamRepository userExamRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ExamRepository examRepo;

    @Override
    public UserExam save(UserExamRequest userExamRequest,Long userId) throws Exception {
        Optional<User> user = userRepo.findById(userId);
        if(user.isEmpty()){
            throw new Exception("not found user");
        }

        Optional<Exam> exam = examRepo.findById(userExamRequest.getExamId());
        if(exam.isEmpty()){
            throw new Exception("exam not found");
        }

        UserExam userExam = new UserExam(user.get(), exam.get(), userExamRequest.getAnswer());
        userExamRepo.save(userExam);

        userExam.setNumberOfCorrectAnswer(caculateCorrectAnswerOfExam(exam.get(),userExamRequest.getAnswer()));

        return userExam;
    }


    @Override
    public Long caculateCorrectAnswerOfExam(Exam exam, List<String> answers) {
        Long countCorrectAnswer = 0L;

        for(int i=0; i<answers.size(); i++){
            if(exam.getQuestions().get(i).getCorrectAnswer().equalsIgnoreCase(answers.get(i))){
                countCorrectAnswer++;
            }
        }

        return countCorrectAnswer;
    }

    @Override
    public List<StatisticalResponse> getAllUserExamOfEachUserHasBestScore() {
        List<UserExam> userExams = userExamRepo.findAllExamWithBestScoreOfUser();
        List<StatisticalResponse> statisticalResponses = userExams.stream().map(i -> new StatisticalResponse(i)).collect(Collectors.toList());
        return statisticalResponses;
    }

    @Override
    public List<StatisticalResponse> getAllUserExamOfEachUserHasBestScoreByDate(String date) {
        List<UserExam> userExams = userExamRepo.findAllExamWithBestScoreByDate(date);
        List<StatisticalResponse> statisticalResponses = userExams.stream().map(i -> new StatisticalResponse(i)).collect(Collectors.toList());
        return statisticalResponses;
    }

    @Override
    public List<StatisticalResponse> getAllUserExamOfEachUserHasBestScoreByName(String examName) {
        List<UserExam> userExams = userExamRepo.findAllExamWithBestScoreByName(examName);
        List<StatisticalResponse> statisticalResponses = userExams.stream().map(i -> new StatisticalResponse(i)).collect(Collectors.toList());
        return statisticalResponses;
    }

    @Override
    public List<StatisticalResponse> getAllUserExamOfEachUserHasBestScoreByNameAndDate(String examName, String date) {
        List<UserExam> userExams = userExamRepo.findAllExamWithBestScoreByNameAndDate(examName,date);
        List<StatisticalResponse> statisticalResponses = userExams.stream().map(i -> new StatisticalResponse(i)).collect(Collectors.toList());
        return statisticalResponses;
    }
}
