package com.attendance.microservices.attendanceapp.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.attendance.microservices.attendanceapp.dto.AttendanceDataDTO;
import com.attendance.microservices.attendanceapp.dto.AttendanceDetailsSubjectResponse;
import com.attendance.microservices.attendanceapp.dto.AttendanceSubjectDetails;
import com.attendance.microservices.attendanceapp.entities.Attendance;
import com.attendance.microservices.attendanceapp.repository.AttendanceRepository;
import com.attendance.microservices.attendanceapp.services.AttendanceService;

@Service
public class AttendenceServiceImpl implements AttendanceService{

    private boolean takingAttendance = false;
    private AttendanceSubjectDetails subjectContext;

    @Autowired
    AttendanceRepository attendanceRepository;


    // Start taking attendance, i.e. set "takingAttendance" = true
    @Override
    public void startTakingAttendance() {
        this.takingAttendance = true;
    }


    // Set the subject context while taking attendance
    @Override
    public void setSubjectContext(AttendanceSubjectDetails subjectDetails) {
        this.subjectContext = subjectDetails;
    }


    // Get All attendance details of all students
    @Override
    public List<Attendance> getAttendanceDetails() {
        return attendanceRepository.findAll();
    }


    @Override
    public AttendanceSubjectDetails getSubjectContext() {
        return this.subjectContext;
    }


    @Override
    public boolean getTakingAttendance() {
        return this.takingAttendance;
    }


    // Get Attendance Data by Subject
    @Override
    public List<AttendanceDetailsSubjectResponse> getAttendanceDetailsBySubjectId(String subjectId) {

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


    // Process Incoming IDs from the IoT device
    @Override
    public void processIncomingIds(String studentId) {
        // Check if subjectContext is set
        if (takingAttendance && subjectContext != null) {
            // Associate studentIds with subject details and update the attendance table
            processAttendance(subjectContext, studentId);
        } else {
            // Log an error or handle the case where subjectContext is not set
        }
    }


    // Process attendance for one student with subject context
    private void processAttendance(AttendanceSubjectDetails subjectDetails, String studentId) {
        // Logic to associate studentIds with subject details and update the attendance table
        System.out.println("Subject: " + subjectDetails.getSubjectID());
        System.out.println("Date: " + subjectDetails.getDate());
        System.out.println("Student IDs: " + studentId);

        // Implement the logic to update the attendance table
    }


    // On "STOP", stop taking attendance
    @Override
    public void stopTakingAttendance() {
        // Stop taking attendance
        if(takingAttendance) {
            this.takingAttendance = false;
            this.subjectContext = null;
        }
        // String deviceId = "your-device-id";
        // String stopPayload = "{\"state\":{\"desired\":{\"attendance\":\"STOP\"}}}";
        // AwsIotMqttClient.publish(deviceId, stopPayload);
    }


    // If "STOP" attendance not sent, stop taking attendance automatically, check if taking attendance, if taking
    // then only "STOP"
    @Override
    @Scheduled(fixedDelay = 1800000) // 30 minutes in milliseconds
    public void stopTakingAttendanceAutomatically() {
        // Stop taking attendance
        if(takingAttendance) {
            this.takingAttendance = false;
            this.subjectContext = null;
        }
        // String deviceId = "your-device-id";
        // String stopPayload = "{\"state\":{\"desired\":{\"attendance\":\"STOP\"}}}";
        // AwsIotMqttClient.publish(deviceId, stopPayload);
    }
}
