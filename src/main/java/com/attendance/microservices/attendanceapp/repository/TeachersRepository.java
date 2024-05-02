package com.attendance.microservices.attendanceapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.attendance.microservices.attendanceapp.entities.Teachers;

public interface TeachersRepository extends JpaRepository<Teachers, Integer>{
        Teachers findFirstByUserUsername(String username);
        List<Teachers> findAllByDeptId(int deptID);
}
