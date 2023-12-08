package com.attendance.microservices.attendanceapp.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.attendance.microservices.attendanceapp.entities.Users;
import com.attendance.microservices.attendanceapp.repository.UserRepository;
import com.attendance.microservices.attendanceapp.services.UserService;

public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository usersRepository;

    @Override
    public List<Users> getUsersDetails() { 
        return usersRepository.findAll();
    }
    
}
