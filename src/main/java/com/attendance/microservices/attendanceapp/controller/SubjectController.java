package com.attendance.microservices.attendanceapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.attendance.microservices.attendanceapp.entities.Subjects;
import com.attendance.microservices.attendanceapp.services.SubjectService;

@CrossOrigin
@RestController
@RequestMapping("api/v1/subjects")
public class SubjectController {
    @Autowired
    SubjectService subjectService;

    @GetMapping("/details")
    public List<Subjects> getSubjectDetails(){
        return subjectService.getSubjectDetails();
    }
}
