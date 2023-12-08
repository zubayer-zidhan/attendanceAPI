package com.attendance.microservices.attendanceapp.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.attendance.microservices.attendanceapp.entities.Departments;

@Service
public interface DepartmentService {
    public List<Departments> getDepartmentDetails();
}
