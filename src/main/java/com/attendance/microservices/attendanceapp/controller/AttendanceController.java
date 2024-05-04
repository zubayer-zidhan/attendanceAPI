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

import com.attendance.microservices.attendanceapp.dto.ApiResponse;
import com.attendance.microservices.attendanceapp.dto.AttendanceDetailsSubjectResponse;
import com.attendance.microservices.attendanceapp.dto.AttendanceRecordRequestDTO;
import com.attendance.microservices.attendanceapp.dto.AttendanceSubjectDetails;
import com.attendance.microservices.attendanceapp.dto.IotSignalDTO;
import com.attendance.microservices.attendanceapp.dto.ReaderTypeDTO;
import com.attendance.microservices.attendanceapp.dto.TakeAttendanceResponseDTO;
import com.attendance.microservices.attendanceapp.dto.TakingAttendanceDTO;
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

    @GetMapping("/details/{subjectId}/{date}/{classNumber}")
    public List<AttendanceDetailsSubjectResponse> getAttendanceDetailsBySubjectIdAndDate(
            @PathVariable String subjectId,
            @PathVariable String date,
            @PathVariable int classNumber) {
        return attendanceService.getAttendanceDetailsBySubjectIdAndDateAndClassNumber(subjectId, date, classNumber);
    }

    // Start taking attendance
    @PostMapping("/takeAttendance")
    public ResponseEntity<ApiResponse<TakeAttendanceResponseDTO>> takeAttendance(
            @RequestBody AttendanceSubjectDetails subjectDetails) {
        ApiResponse<TakeAttendanceResponseDTO> response = new ApiResponse<>();

        // Save subjectDetails in a context variable or database
        // This context will be used to associate incoming IDs with subject details
        if (!attendanceService.getTakingAttendance()) {
            attendanceService.startTakingAttendance();
            attendanceService.setSubjectContext(subjectDetails);

            // Send the "ON" signal to the IoT device
            IotSignalDTO onSignal = IotSignalDTO.builder().message("ON").build();
            messagingTemplate.convertAndSend("/topic/signal", onSignal);

            TakeAttendanceResponseDTO data = this.getAttendanceSessionData();

            response.setSuccess(true);
            response.setMessage("Attendance Started.");
            response.setData(data);

            System.out.println(response);

            return ResponseEntity.ok(response);
        }

        TakeAttendanceResponseDTO data = this.getAttendanceSessionData();

        response.setSuccess(true);
        response.setData(data);
        response.setMessage("Already Taking Attendance.");

        System.out.println(response);
        
        return ResponseEntity.ok(response);

    }

    // Stop taking attendance
    @PostMapping("/endAttendance")
    public ResponseEntity<ApiResponse<TakeAttendanceResponseDTO>> endAttendance() {
        ApiResponse<TakeAttendanceResponseDTO> response = new ApiResponse<>();

        // Save previous context values to output context
        TakeAttendanceResponseDTO data = this.getAttendanceSessionData();

        // Stop Taking Attendance, set everything to null
        attendanceService.stopTakingAttendance();

        // Send the "OFF" signal to the IoT device
        IotSignalDTO offSignal = IotSignalDTO.builder().message("OFF").build();
        messagingTemplate.convertAndSend("/topic/signal", offSignal);

        data.setTakingAttendance(false);
        response.setSuccess(true);
        response.setMessage("Attendance Stopped.");
        response.setData(data);

        System.out.println(response.getData());

        return ResponseEntity.ok(response);
    }

    // Handle incoming attendance requests
    @PostMapping("/publish")
    public ResponseEntity<String> publishAttendanceRecord(@RequestBody AttendanceRecordRequestDTO request) {
        return attendanceService.processIncomingIds(request);
    }

    // Get Taking Attendance Value
    @GetMapping("/getTakingAttendance")
    public TakingAttendanceDTO getMethodName() {

        TakingAttendanceDTO takingAttendanceDTO = TakingAttendanceDTO.builder()
                .takingAttendance(attendanceService.getTakingAttendance())
                .build();

        return takingAttendanceDTO;
    }

    // Set Reader Type (RFID/Barcode)
    @PostMapping("/setReaderType")
    public ResponseEntity<String> setReaderType(@RequestBody ReaderTypeDTO request) {
        return attendanceService.setReaderType(request.getReaderID());
    }

    // Get current reader type
    @GetMapping("/getReaderType")
    public ResponseEntity<String> getReaderType() {
        return attendanceService.getReaderType();
    }

    public TakeAttendanceResponseDTO getAttendanceSessionData() {
        // Get context from attendance service
        AttendanceSubjectDetails subjectContext = attendanceService.getSubjectContext();

        TakeAttendanceResponseDTO data = TakeAttendanceResponseDTO.builder()
                .takingAttendance(attendanceService.getTakingAttendance())
                .subjectID(subjectContext.getSubjectID())
                .date(subjectContext.getDate())
                .classNumber(attendanceService.getClassNumber())
                .proxy(subjectContext.isProxy())
                .build();
        return data;
    }
}
