package com.example.demo.repository;

import com.example.demo.model.UserExam;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UserExamRepository extends JpaRepository<UserExam,Long> {

    void deleteAllByUserId(Long userId);

    void deleteAllByExamId(Long examId);

    @Query("SELECT DISTINCT ue FROM UserExam ue "+
            "WHERE ue.numberOfCorrectAnswer = (SELECT MAX(ue2.numberOfCorrectAnswer) " +
            "FROM UserExam ue2 WHERE ue2.exam.id = ue.exam.id AND ue2.user.id = ue.user.id) "+
            "ORDER BY ue.id, ue.answer, ue.doAt, ue.exam.id, ue.numberOfCorrectAnswer, ue.user.id")
    List<UserExam> findAllExamWithBestScoreOfUser();


    @Query("SELECT DISTINCT ue FROM UserExam ue "+
            "WHERE ue.exam.name = ?1 AND ue.numberOfCorrectAnswer = (SELECT MAX(ue2.numberOfCorrectAnswer) " +
            "FROM UserExam ue2 WHERE ue2.exam.id = ue.exam.id AND ue2.user.id = ue.user.id) "+
            "ORDER BY ue.id, ue.answer, ue.doAt, ue.exam.id, ue.numberOfCorrectAnswer, ue.user.id")
    List<UserExam> findAllExamWithBestScoreByName(String name);

    @Query("SELECT DISTINCT ue FROM UserExam ue "+
            "WHERE ue.doAt = ?1 AND ue.numberOfCorrectAnswer = (SELECT MAX(ue2.numberOfCorrectAnswer) " +
            "FROM UserExam ue2 WHERE ue2.exam.id = ue.exam.id AND ue2.user.id = ue.user.id) "+
            "ORDER BY ue.id, ue.answer, ue.doAt, ue.exam.id, ue.numberOfCorrectAnswer, ue.user.id")
    List<UserExam> findAllExamWithBestScoreByDate(String date);

    @Query("SELECT DISTINCT ue FROM UserExam ue "+
            "WHERE ue.exam.name = ?1 AND ue.doAt = ?2 AND ue.numberOfCorrectAnswer = (SELECT MAX(ue2.numberOfCorrectAnswer) " +
            "FROM UserExam ue2 WHERE ue2.exam.id = ue.exam.id AND ue2.user.id = ue.user.id) "+
            "ORDER BY ue.id, ue.answer, ue.doAt, ue.exam.id, ue.numberOfCorrectAnswer, ue.user.id")
    List<UserExam> findAllExamWithBestScoreByNameAndDate(String name, String date);

}
