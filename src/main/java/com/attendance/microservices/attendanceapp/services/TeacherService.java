package com.attendance.microservices.attendanceapp.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.attendance.microservices.attendanceapp.entities.Teachers;
@Service
public interface TeacherService {
    public List<Teachers> getTeacherDetails();
}
