package com.attendance.microservices.attendanceapp.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.attendance.microservices.attendanceapp.entities.Users;
@Service
public interface UserService {
    public List<Users> getUsersDetails();
}
