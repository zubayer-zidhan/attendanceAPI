package com.attendance.microservices.attendanceapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.attendance.microservices.attendanceapp.entities.Teachers;

public interface TeachersRepository extends JpaRepository<Teachers, Integer>{
    
}
