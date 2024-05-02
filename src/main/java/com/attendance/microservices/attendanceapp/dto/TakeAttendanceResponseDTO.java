package com.attendance.microservices.attendanceapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TakeAttendanceResponseDTO {
    boolean takingAttendance;
    String subjectID;
    String date;
    int classNumber;
    boolean proxy;
}
