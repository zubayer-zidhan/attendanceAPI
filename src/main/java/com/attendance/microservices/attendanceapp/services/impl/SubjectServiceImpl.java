package com.attendance.microservices.attendanceapp.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.attendance.microservices.attendanceapp.entities.Subjects;
import com.attendance.microservices.attendanceapp.repository.SubjectsRepository;
import com.attendance.microservices.attendanceapp.services.SubjectService;

@Service
public class SubjectServiceImpl implements SubjectService {
    @Autowired
    SubjectsRepository subjectsRepository;

    @Override
    public List<Subjects> getSubjectDetails() {
        return subjectsRepository.findAll();
    }
    


}
