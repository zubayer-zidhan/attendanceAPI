package com.attendance.microservices.attendanceapp.services;

import java.util.List;

import com.attendance.microservices.attendanceapp.entities.Teachers;

public interface TeacherService {
    public List<Teachers> getTeacherDetails();
}
