package com.attendance.microservices.attendanceapp.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.attendance.microservices.attendanceapp.entities.Departments;
import com.attendance.microservices.attendanceapp.repository.DepartmentsRepository;
import com.attendance.microservices.attendanceapp.services.DepartmentService;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    DepartmentsRepository departmentsRepository;

    @Override
    public List<Departments> getDepartmentDetails() {
        return departmentsRepository.findAll();
    }
    
}
