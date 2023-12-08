package com.attendance.microservices.attendanceapp.services.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.attendance.microservices.attendanceapp.entities.Students;
import com.attendance.microservices.attendanceapp.repository.StudentsRepository;
import com.attendance.microservices.attendanceapp.services.StudentsService;


@Service
public class StudentsServiceImpl implements StudentsService{
    @Autowired
    StudentsRepository studentsRepository;

    @Override
    public List<Students> getStudentDetails() {
        
        return studentsRepository.findAll();
    }

    

}
