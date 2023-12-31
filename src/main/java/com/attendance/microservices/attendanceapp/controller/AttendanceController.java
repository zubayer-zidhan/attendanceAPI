package com.attendance.microservices.attendanceapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.attendance.microservices.attendanceapp.dto.AttendanceDetailsSubjectResponse;
import com.attendance.microservices.attendanceapp.entities.Attendance;
import com.attendance.microservices.attendanceapp.services.AttendanceService;
import org.springframework.web.bind.annotation.RequestParam;


@CrossOrigin
@RestController
@RequestMapping("api/v1/attendance")
public class AttendanceController {
    @Autowired
    AttendanceService attendanceService;

    @GetMapping("/details")
    public List <Attendance> getAttendanceDetails(){
        return attendanceService.getAttendanceDetails();
    }

    @GetMapping("/details/{subjectId}")
    public List<AttendanceDetailsSubjectResponse> getAttendanceDetailsBySubjectId(
        @PathVariable String subjectId
    ){
        return attendanceService.getAttendanceDetailsBySubjectId(subjectId);
    }
    
}
