package com.attendance.microservices.attendanceapp.services;

import java.util.List;

import com.attendance.microservices.attendanceapp.entities.Attendance;

public interface AttendanceService {
    public List<Attendance> getAttendanceDetails();
}
