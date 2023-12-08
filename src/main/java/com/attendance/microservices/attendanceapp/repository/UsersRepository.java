package com.attendance.microservices.attendanceapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.attendance.microservices.attendanceapp.entities.Users;

public interface UsersRepository extends JpaRepository<Users,Integer>{
    
}
