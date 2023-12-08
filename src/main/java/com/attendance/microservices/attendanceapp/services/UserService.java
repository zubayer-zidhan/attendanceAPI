package com.attendance.microservices.attendanceapp.services;

import java.util.List;

import com.attendance.microservices.attendanceapp.entities.Users;

public interface UserService {
    public List<Users> getUsersDetails();
}
