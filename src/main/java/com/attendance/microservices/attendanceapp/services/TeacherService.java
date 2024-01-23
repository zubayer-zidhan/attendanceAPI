package com.attendance.microservices.attendanceapp.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.attendance.microservices.attendanceapp.dto.TeacherDetailsResponse;
@Service
public interface TeacherService {
    public List<TeacherDetailsResponse> getTeacherSubjects(String username);
}
