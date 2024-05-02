package com.attendance.microservices.attendanceapp.services;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.attendance.microservices.attendanceapp.dto.AssignProxyDTO;
import com.attendance.microservices.attendanceapp.dto.AssignProxyRequestDetails;
import com.attendance.microservices.attendanceapp.entities.Departments;

@Service
public interface DepartmentService {
    public List<Departments> getDepartmentDetails();
    public AssignProxyRequestDetails getTeachersAndSubjects(String username);
    public ResponseEntity<String> assignProxy(AssignProxyDTO request);
}
