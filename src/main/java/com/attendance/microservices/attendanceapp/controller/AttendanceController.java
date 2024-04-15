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
import com.attendance.microservices.attendanceapp.dto.AttendanceRecordRequestDTO;
import com.attendance.microservices.attendanceapp.dto.AttendanceSubjectDetails;
import com.attendance.microservices.attendanceapp.dto.IotSignalDTO;
import com.attendance.microservices.attendanceapp.entities.Attendance;
import com.attendance.microservices.attendanceapp.services.AttendanceService;

@CrossOrigin
@RestController
@RequestMapping("api/v1/attendance")
public class AttendanceController {

    @Autowired
    AttendanceService attendanceService;

    @Autowired
    SimpMessagingTemplate messagingTemplate;

    @GetMapping("/details")
    public List<Attendance> getAttendanceDetails() {
        return attendanceService.getAttendanceDetails();
    }

    @GetMapping("/details/{subjectId}")
    public List<AttendanceDetailsSubjectResponse> getAttendanceDetailsBySubjectId(
            @PathVariable String subjectId) {
        return attendanceService.getAttendanceDetailsBySubjectId(subjectId);
    }

    @GetMapping("/details/{subjectId}/{date}")
    public List<AttendanceDetailsSubjectResponse> getAttendanceDetailsBySubjectIdAndDate(
            @PathVariable String subjectId,
            @PathVariable String date) {
        return attendanceService.getAttendanceDetailsBySubjectIdAndDate(subjectId, date);
    }

    // Start taking attendance
    @PostMapping("/takeAttendance")
    public ResponseEntity<String> takeAttendance(@RequestBody AttendanceSubjectDetails subjectDetails) {

        // Save subjectDetails in a context variable or database
        // This context will be used to associate incoming IDs with subject details
        if (!attendanceService.getTakingAttendance()) {
            attendanceService.startTakingAttendance();
            attendanceService.setSubjectContext(subjectDetails);

            System.out.println(attendanceService.getTakingAttendance());
            System.out.println(attendanceService.getSubjectContext());

            // Send the "ON" signal to the IoT device
            IotSignalDTO onSignal = IotSignalDTO.builder().message("ON").build();
            messagingTemplate.convertAndSend("/topic/signal", onSignal);

            return ResponseEntity.ok("Attendance started.");
        }
        return ResponseEntity.ok("Already Taking Attendance.");

    }

    // Stop taking attendance
    @PostMapping("/endAttendance")
    public ResponseEntity<String> endAttendance() {
        // Save subjectDetails in a context variable or database
        // This context will be used to associate incoming IDs with subject details
        attendanceService.stopTakingAttendance();

        System.out.println(attendanceService.getTakingAttendance());
        System.out.println(attendanceService.getSubjectContext());

        // Send the "OFF" signal to the IoT device
        IotSignalDTO offSignal = IotSignalDTO.builder().message("OFF").build();
        messagingTemplate.convertAndSend("/topic/signal", offSignal);

        return ResponseEntity.ok("Attendance stopped.");
    }


    // Handle incoming attendance requests
    @PostMapping("/publish")
    public ResponseEntity<String> publishAttendanceRecord(@RequestBody AttendanceRecordRequestDTO request) {
        return attendanceService.processIncomingIds(request);
    }
}
