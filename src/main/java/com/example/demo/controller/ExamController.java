package com.example.demo.controller;

import com.example.demo.dto.ExamDto;
import com.example.demo.model.Exam;
import com.example.demo.model.UserExam;
import com.example.demo.request.ExamRequest;
import com.example.demo.request.TextRequest;
import com.example.demo.request.UserExamRequest;
import com.example.demo.security.jwt.JwtProvider;
import com.example.demo.service.impl.ExamServiceImpl;
import com.example.demo.service.impl.UserExamServiceImpl;
import com.example.demo.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/exam")
public class ExamController {

    @Autowired
    private ExamServiceImpl examService;

    @Autowired
    private UserExamServiceImpl userExamService;

    @Autowired
    private JwtProvider jwtProvider;

    @GetMapping("/all")
    public ResponseEntity<?> getAllExam(){
        List<Exam> exams = examService.getAllExam();
        List<ExamDto> examDtos = exams.stream().map(i -> new ExamDto(i)).collect(Collectors.toList());
        return new ResponseEntity<>(examDtos, HttpStatus.OK);
    }


    @GetMapping("/{examId}")
    public ResponseEntity<?> getExamById(@PathVariable Long examId){
        Optional<Exam> exam = examService.getExamById(examId);
        if(exam.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(exam.get(),HttpStatus.OK);
    }

    @PostMapping("/do")
    public ResponseEntity<?> doExam(@RequestBody UserExamRequest userExamRequest, HttpServletRequest request) throws Exception {
        Long userId = jwtProvider.getIdFromHttpRequest(request);


        UserExam userExam =  userExamService.save(userExamRequest,userId);
       return new ResponseEntity<>(userExam,HttpStatus.CREATED);
    }

    @GetMapping("/search")
    public  ResponseEntity<?> searchExam(@RequestBody TextRequest request){
        List<Exam> exams = examService.seacrchExam(request.getText());
        List<ExamDto> examDtos = exams.stream().map(i -> new ExamDto(i)).collect(Collectors.toList());
        return new ResponseEntity<>(examDtos,HttpStatus.OK);
    }


}
