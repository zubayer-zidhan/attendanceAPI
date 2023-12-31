package com.attendance.microservices.attendanceapp.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.attendance.microservices.attendanceapp.dto.AttendanceDetailsSubjectResponse;
import com.attendance.microservices.attendanceapp.entities.Attendance;

@Service
public interface AttendanceService {
    public List<Attendance> getAttendanceDetails();
    public List<AttendanceDetailsSubjectResponse> getAttendanceDetailsBySubjectId(String subjectId);
}
