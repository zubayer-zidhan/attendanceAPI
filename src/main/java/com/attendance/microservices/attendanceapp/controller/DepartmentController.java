package com.attendance.microservices.attendanceapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.attendance.microservices.attendanceapp.entities.Departments;
import com.attendance.microservices.attendanceapp.services.DepartmentService;

@CrossOrigin
@RestController
@RequestMapping("api/departments")
public class DepartmentController {
    @Autowired
    DepartmentService departmentService;
    
    @GetMapping("/details")
    public List<Departments> getDepartmentDetails(){
        return departmentService.getDepartmentDetails();
    }
}
