package com.attendance.microservices.attendanceapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.attendance.microservices.attendanceapp.dto.TeacherDetailsResponse;
import com.attendance.microservices.attendanceapp.services.TeacherService;

@CrossOrigin
@RestController
@RequestMapping("api/v1/teachers")
public class TeacherController {
    @Autowired
    TeacherService teacherService;

    // Get all details for a given teacher
    @GetMapping("/details/{username}")
    public TeacherDetailsResponse getTeacherSubjects(
        @PathVariable String username
    ){
        return teacherService.getTeacherSubjects(username);
    }
}
