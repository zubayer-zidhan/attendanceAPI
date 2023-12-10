package com.attendance.microservices.attendanceapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.attendance.microservices.attendanceapp.entities.Attendance;

public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {
   List<Attendance> findAllBySubjectIdOrderByStudentRollNumber(String subjectId);
}
