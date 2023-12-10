package com.attendance.microservices.attendanceapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * AttendanceDataDTO
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceDataDTO {
    String date;
    boolean present;
    
}