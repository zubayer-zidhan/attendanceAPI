package com.attendance.microservices.attendanceapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.attendance.microservices.attendanceapp.entities.StudentSubjects;

import java.util.List;

public interface StudentSubjectsRepository extends JpaRepository<StudentSubjects, Integer> {
    List<StudentSubjects> findAllBySubjectId(String subjectId);
}
