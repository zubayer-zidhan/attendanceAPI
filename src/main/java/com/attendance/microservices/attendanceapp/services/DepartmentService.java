package com.attendance.microservices.attendanceapp.services;

import java.util.List;

import com.attendance.microservices.attendanceapp.entities.Departments;

public interface DepartmentService {
    public List<Departments> getDepartmentDetails();
}
