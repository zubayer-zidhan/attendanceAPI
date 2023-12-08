package com.attendance.microservices.attendanceapp.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.attendance.microservices.attendanceapp.entities.Subjects;
import com.attendance.microservices.attendanceapp.repository.SubjectsRepository;
import com.attendance.microservices.attendanceapp.services.SubjectService;

public class SubjectServiceImpl implements SubjectService {
    @Autowired
    SubjectsRepository subjectsRepository;

    @Override
    public List<Subjects> getSubjectDetails() {
        return subjectsRepository.findAll();
    }
    


}
