package com.attendance.microservices.attendanceapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.attendance.microservices.attendanceapp.entities.Attendance;

public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {
   List<Attendance> findAllBySubjectIdOrderByStudentRollNumber(String subjectId);
   List<Attendance> findAllBySubjectIdAndDate(String subjectId, String date);
   List<Attendance> findAllBySubjectIdAndDateAndClassNumber(String subjectId, String date, int classNumber);

   @Query("SELECT COALESCE(MAX(a.classNumber), 0) FROM Attendance a WHERE a.subject.id = :subjectId AND a.date = :date")
   int findMaxClassNumberBySubjectIdAndDate(String subjectId, String date);
}
