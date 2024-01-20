package com.attendance.microservices.attendanceapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.attendance.microservices.attendanceapp.entities.StudentSubjects;

public interface StudentSubjectsRepository extends JpaRepository<StudentSubjects, Integer> {
    
}
