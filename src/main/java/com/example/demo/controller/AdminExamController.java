package com.example.demo.controller;

import com.example.demo.model.Exam;
import com.example.demo.reponse.StatisticalResponse;
import com.example.demo.request.ExamRequest;
import com.example.demo.request.NameDateRequest;
import com.example.demo.request.TextRequest;
import com.example.demo.service.impl.ExamServiceImpl;
import com.example.demo.service.impl.UserExamServiceImpl;
import com.example.demo.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/exam")
public class AdminExamController {
    @Autowired
    private ExamServiceImpl examService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserExamServiceImpl userExamService;

    @PostMapping("/save")
    public ResponseEntity<?> saveExam(@RequestBody ExamRequest examRequest){

        Exam exam = examService.saveExam(examRequest);
        return new ResponseEntity<>(exam,HttpStatus.CREATED);

    }

    @PatchMapping("/{examId}")
    public ResponseEntity<?> changeExamById(@PathVariable Long examId,@RequestBody ExamRequest examRequest){
        Exam exam = examService.changeExam(examId,examRequest);
        return new ResponseEntity<>(exam,HttpStatus.OK);
    }

    @DeleteMapping("/{examId}")
    public ResponseEntity<?> deleteExamById(@PathVariable Long examId){
        examService.deleteExamById(examId);
        return new ResponseEntity<>("bài kiểm tra đã được xóa",HttpStatus.OK);
    }

    @DeleteMapping()
    public ResponseEntity<?> deleteAllExam(){
        examService.deleteAllExam();
        return new ResponseEntity<>("Đã xóa hết tất cả các bài kiểm tra",HttpStatus.OK);
    }

    @GetMapping("/statistical/all")
    public ResponseEntity<?> getAllExamScore(){
        List<StatisticalResponse> statisticalResponses = userExamService.getAllUserExamOfEachUserHasBestScore();
        return new ResponseEntity<>(statisticalResponses,HttpStatus.OK);
    }

    @GetMapping("/statistical/name")
    public ResponseEntity<?> getAllExamForName(@RequestBody TextRequest request){
        if (request.getText() == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<StatisticalResponse> statisticalResponses = userExamService.getAllUserExamOfEachUserHasBestScoreByName(request.getText());
        return new ResponseEntity<>(statisticalResponses,HttpStatus.OK);
    }

    @GetMapping("/statistical/date")
    public ResponseEntity<?> getAllExamForDate(@RequestBody TextRequest request){
        if (request.getText() == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<StatisticalResponse> statisticalResponses = userExamService.getAllUserExamOfEachUserHasBestScoreByDate(request.getText());
        return new ResponseEntity<>(statisticalResponses,HttpStatus.OK);
    }

    @GetMapping("/statistical/date_name")
    public ResponseEntity<?> getAllExamForDateAndName(@RequestBody NameDateRequest request){
        if (request.getName() == null || request.getDate() == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<StatisticalResponse> statisticalResponses = userExamService.getAllUserExamOfEachUserHasBestScoreByNameAndDate(request.getName(),request.getDate());
        return new ResponseEntity<>(statisticalResponses,HttpStatus.OK);
    }

    @GetMapping("/all_name")
    public ResponseEntity<?> getAllExamName(){
        List<String> examNames = examService.getAllExamName();
        return new ResponseEntity<>(examNames,HttpStatus.OK);
    }
}

