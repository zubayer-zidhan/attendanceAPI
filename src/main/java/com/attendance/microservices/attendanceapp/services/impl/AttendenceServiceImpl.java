package com.attendance.microservices.attendanceapp.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.attendance.microservices.attendanceapp.entities.Attendance;
import com.attendance.microservices.attendanceapp.repository.AttendanceRepository;
import com.attendance.microservices.attendanceapp.services.AttendanceService;

@Service
public class AttendenceServiceImpl implements AttendanceService{
    @Autowired
    AttendanceRepository attendanceRepository;

    @Override
    public List<Attendance> getAttendanceDetails() {
        return attendanceRepository.findAll();
    } 
    
    
}
