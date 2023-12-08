package com.attendance.microservices.attendanceapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.attendance.microservices.attendanceapp.entities.Students;
import com.attendance.microservices.attendanceapp.services.StudentsService;

@CrossOrigin
@RestController
@RequestMapping("api/students")
public class StudentController {
    @Autowired
    StudentsService studentsService;
    
    @GetMapping("/details")
    public List<Students> getStudentsDetails(){
        return studentsService.getStudentDetails();
    }
}
