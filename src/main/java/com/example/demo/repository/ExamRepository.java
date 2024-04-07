package com.example.demo.repository;

import com.example.demo.model.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamRepository extends JpaRepository<Exam,Long> {

    @Query("select a from Exam a where a.name like ?1 or a.type like ?1")
    List<Exam> searchExam(String name);

    @Query("Select a.name from Exam a ORDER BY a.name")
    List<String> findAllExamName();
}
