package com.attendance.microservices.attendanceapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.attendance.microservices.attendanceapp.entities.SubjectTeachers;

public interface SubjectTeachersRepository extends JpaRepository<SubjectTeachers, Integer> {
    
}
