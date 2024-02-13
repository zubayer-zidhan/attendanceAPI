package com.attendance.microservices.attendanceapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.attendance.microservices.attendanceapp.dto.AttendanceDetailsSubjectResponse;
import com.attendance.microservices.attendanceapp.dto.AttendanceSubjectDetails;
import com.attendance.microservices.attendanceapp.entities.Attendance;
import com.attendance.microservices.attendanceapp.services.AttendanceService;


@CrossOrigin
@RestController
@RequestMapping("api/v1/attendance")
public class AttendanceController {

    @Autowired
    AttendanceService attendanceService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

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

    // Start taking attendance
    @PostMapping("/takeAttendance")
    public ResponseEntity<String> takeAttendance(@RequestBody AttendanceSubjectDetails subjectDetails) {
        
        // Save subjectDetails in a context variable or database
        // This context will be used to associate incoming IDs with subject details
        attendanceService.startTakingAttendance();
        attendanceService.setSubjectContext(subjectDetails);


        System.out.println(attendanceService.getTakingAttendance());
        System.out.println(attendanceService.getSubjectContext());
        
        // Send the "ON" signal to the IoT device
        messagingTemplate.convertAndSend("/topic/signal", "ON");

        return ResponseEntity.ok("Attendance started.");
    }


    // Stop taking attendance
    @PostMapping("/endAttendance")
    public ResponseEntity<String> endAttendance() {
        // Update Thing Shadow state to "START"
        // iotMqttClient.publish("your-device-id", "{\"state\":{\"desired\":{\"attendance\":\"START\"}}}");

        // Save subjectDetails in a context variable or database
        // This context will be used to associate incoming IDs with subject details
        attendanceService.stopTakingAttendance();

        System.out.println(attendanceService.getTakingAttendance());
        System.out.println(attendanceService.getSubjectContext());

        return ResponseEntity.ok("Attendance stopped.");
    }
    
}
