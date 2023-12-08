package com.attendance.microservices.attendanceapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.attendance.microservices.attendanceapp.entities.Departments;

public interface DepartmentsRepository extends JpaRepository<Departments,Integer>{
    
}
