package com.attendance.microservices.attendanceapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.attendance.microservices.attendanceapp.entities.Users;
import java.util.Optional;


public interface UserRepository extends JpaRepository<Users,Integer>{
   Optional<Users> findByUsername(String username); 
}
