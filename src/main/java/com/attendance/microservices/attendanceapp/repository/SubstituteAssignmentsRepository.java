package com.attendance.microservices.attendanceapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.attendance.microservices.attendanceapp.entities.SubstituteAssignments;

public interface SubstituteAssignmentsRepository extends JpaRepository<SubstituteAssignments, Integer> {
    
}
