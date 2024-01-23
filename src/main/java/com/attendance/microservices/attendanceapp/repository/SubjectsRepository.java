package com.attendance.microservices.attendanceapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.attendance.microservices.attendanceapp.entities.Subjects;

public interface SubjectsRepository extends JpaRepository<Subjects,String> {
    
}
