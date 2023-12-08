package com.attendance.microservices.attendanceapp.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.attendance.microservices.attendanceapp.entities.Users;
import com.attendance.microservices.attendanceapp.repository.UserRepository;
import com.attendance.microservices.attendanceapp.services.UserService;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository usersRepository;

    @Override
    public List<Users> getUsersDetails() { 
        return usersRepository.findAll();
    }
    
}
