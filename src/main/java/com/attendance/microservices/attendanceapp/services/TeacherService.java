package com.attendance.microservices.attendanceapp.services;

import org.springframework.stereotype.Service;

import com.attendance.microservices.attendanceapp.dto.TeacherDetailsResponse;
@Service
public interface TeacherService {
    public TeacherDetailsResponse getTeacherSubjects(String username);
}
