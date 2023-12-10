package com.attendance.microservices.attendanceapp.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceDetailsSubjectResponse {
   String name;
   String rollNumber;
   List<AttendanceDataDTO> attendance;

}
