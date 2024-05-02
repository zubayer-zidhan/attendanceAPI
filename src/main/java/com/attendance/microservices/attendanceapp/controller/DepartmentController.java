package com.attendance.microservices.attendanceapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.attendance.microservices.attendanceapp.dto.AssignProxyDTO;
import com.attendance.microservices.attendanceapp.dto.AssignProxyRequestDetails;
import com.attendance.microservices.attendanceapp.entities.Departments;
import com.attendance.microservices.attendanceapp.services.DepartmentService;


@CrossOrigin
@RestController
@RequestMapping("api/v1/departments")
public class DepartmentController {
    @Autowired
    DepartmentService departmentService;
    
    @GetMapping("/details")
    public List<Departments> getDepartmentDetails(){
        return departmentService.getDepartmentDetails();
    }


    // Get a list of all teachers for a given department
    @GetMapping("/assignProxy/getDetails/{username}")
    public AssignProxyRequestDetails getDetails(@PathVariable String username) {
        return departmentService.getTeachersAndSubjects(username);
    }

    // Assign Proxy Class to a teacher
    @PostMapping("/assignProxy/assign")
    public ResponseEntity<String> assignProxy(@RequestBody AssignProxyDTO request) {
        return departmentService.assignProxy(request);
    }
}
