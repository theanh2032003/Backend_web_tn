package com.example.demo.service.impl;

import com.example.demo.model.Exam;
import com.example.demo.model.Question;
import com.example.demo.repository.ExamRepository;
import com.example.demo.repository.QuestionRepository;
import com.example.demo.repository.UserExamRepository;
import com.example.demo.request.ExamRequest;
import com.example.demo.service.ExamService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExamServiceImpl implements ExamService {

    @Autowired
    private ExamRepository examRepo;

    @Autowired
    private UserExamRepository userExamRepo;

    @Autowired
    private QuestionRepository questionRepo;


    @Override
    public List<Exam> getAllExam() {
        return examRepo.findAll();
    }


    @Override
    public Exam saveExam(ExamRequest examRequest) {
        Exam exam = new Exam(examRequest.getName(), examRequest.getDescription(),examRequest.getType(),
                examRequest.getStartTime(),examRequest.getEndTime(),new ArrayList<>());
        examRepo.save(exam);

        List<Question> questions = new ArrayList<>();

        examRequest.getQuestions().forEach(i->{
            Question question = new Question(i.getQuestion(),i.getOptions(),i.getCorrectAnswer(),exam);
            questionRepo.save(question);
            exam.getQuestions().add(question);
        });

        return exam;
    }

    @Override
    public Optional<Exam> getExamById(Long examId) {
        return examRepo.findById(examId);
    }

    @Transactional
    @Override
    public Exam changeExam(Long examId,ExamRequest examRequest) {

        Exam exam = examRepo.findById(examId).get();
        if(examRequest.getName()!=null){
            exam.setName(examRequest.getName());
        }

        if(examRequest.getDescription() != null){
            exam.setDescription(examRequest.getDescription());
        }

        if(examRequest.getType() != null){
            exam.setType(examRequest.getType());
        }

        if(examRequest.getStartTime() != null){
            exam.setStartTime(examRequest.getStartTime());
        }

        if(examRequest.getEndTime() != null){
            exam.setEndTime(examRequest.getEndTime());
        }

        if(examRequest.getQuestions() != null){
            questionRepo.deleteAllByExamId(examId);
            userExamRepo.deleteAllByExamId(examId);
            exam.getQuestions().clear();
            List<Question> questions = new ArrayList<>();

            examRequest.getQuestions().forEach(i->{
                Question question = new Question(i.getQuestion(),i.getOptions(),i.getCorrectAnswer(),exam);
                questionRepo.save(question);
                exam.getQuestions().add(question);
            });
        }


        return exam;

    }

    @Transactional
    @Override
    public void deleteExamById(Long examId) {
        userExamRepo.deleteAllByExamId(examId);
        questionRepo.deleteAllByExamId(examId);
        examRepo.deleteById(examId);
    }

    @Transactional
    @Override
    public void deleteAllExam() {
        userExamRepo.deleteAll();
        questionRepo.deleteAll();
        examRepo.deleteAll();
    }

    @Override
    public List<Exam> seacrchExam(String name) {
        return examRepo.searchExam(name);
    }

    @Override
    public List<String> getAllExamName() {
        return examRepo.findAllExamName();
    }
}
