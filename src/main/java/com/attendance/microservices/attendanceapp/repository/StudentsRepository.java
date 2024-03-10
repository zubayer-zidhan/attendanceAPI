package com.attendance.microservices.attendanceapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.attendance.microservices.attendanceapp.entities.Students;


public interface StudentsRepository extends JpaRepository<Students, String> {
    Students findFirstByRollNumber(String rollNumber);
}
