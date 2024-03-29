package com.attendance.microservices.attendanceapp.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.attendance.microservices.attendanceapp.dto.AttendanceDetailsSubjectResponse;
import com.attendance.microservices.attendanceapp.dto.AttendanceRecordRequestDTO;
import com.attendance.microservices.attendanceapp.dto.AttendanceSubjectDetails;
import com.attendance.microservices.attendanceapp.entities.Attendance;

@Service
public interface AttendanceService {
    public void startTakingAttendance();
    public void stopTakingAttendance();
    public void stopTakingAttendanceAutomatically();
    public void setSubjectContext(AttendanceSubjectDetails subjectDetails);

    public void processIncomingIds(AttendanceRecordRequestDTO request);

    public List<Attendance> getAttendanceDetails();
    public List<AttendanceDetailsSubjectResponse> getAttendanceDetailsBySubjectId(String subjectId);
    public List<AttendanceDetailsSubjectResponse> getAttendanceDetailsBySubjectIdAndDate(String subjectI, String date);

    public boolean getTakingAttendance();
    public AttendanceSubjectDetails getSubjectContext();
}
