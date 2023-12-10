package com.attendance.microservices.attendanceapp.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.attendance.microservices.attendanceapp.dto.AttendanceDataDTO;
import com.attendance.microservices.attendanceapp.dto.AttendanceDetailsSubjectResponse;
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

    @Override
    public List<AttendanceDetailsSubjectResponse> getAttendanceDetailsBySubjectId(String subjectId) {

        System.out.println(subjectId);

        List<Attendance> attendanceList = attendanceRepository.findAllBySubjectIdOrderByStudentRollNumber(subjectId);

        Map<String, AttendanceDetailsSubjectResponse> studentAttendanceMap = new HashMap<>();

        for (Attendance attendance : attendanceList) {
            // Check if the student is already in the map
            if (studentAttendanceMap.containsKey(attendance.getStudent().getRollNumber())) {
                // If yes, add the attendance data to the existing entry
                AttendanceDetailsSubjectResponse existingDetails = studentAttendanceMap.get(attendance.getStudent().getRollNumber());
                AttendanceDataDTO tempDTO = AttendanceDataDTO.builder()
                        .date(attendance.getDate())
                        .present(attendance.isPresent())
                        .build();
        
                existingDetails.getAttendance().add(tempDTO);
            } else {
                // If no, create a new entry for the student
                List<AttendanceDataDTO> tempList = new ArrayList<>();
                AttendanceDataDTO tempDTO = AttendanceDataDTO.builder()
                        .date(attendance.getDate())
                        .present(attendance.isPresent())
                        .build();
                tempList.add(tempDTO);
        
                AttendanceDetailsSubjectResponse temp = AttendanceDetailsSubjectResponse.builder()
                        .name(attendance.getStudent().getName())
                        .attendance(tempList)
                        .rollNumber(attendance.getStudent().getRollNumber())
                        .build();
        
                studentAttendanceMap.put(attendance.getStudent().getRollNumber(), temp);
            }
        }
        
        // Convert the values of the map to a list
        List<AttendanceDetailsSubjectResponse> attendanceDetails = new ArrayList<>(studentAttendanceMap.values());
        
        return attendanceDetails;


    } 
    
    
}
