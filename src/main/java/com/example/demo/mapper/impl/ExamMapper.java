package com.example.demo.mapper.impl;

import com.example.demo.dto.ExamDto;
import com.example.demo.mapper.ObjectMapper;
import com.example.demo.model.Exam;

public class ExamMapper implements ObjectMapper<Exam, ExamDto> {
    @Override
    public ExamDto mapTo(Exam exam) {
        return new ExamDto(exam);
    }

    @Override
    public Exam mapFrom(ExamDto examDto) {
        return null;
    }
}
