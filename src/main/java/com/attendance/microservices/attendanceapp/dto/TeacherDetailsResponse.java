package com.attendance.microservices.attendanceapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeacherDetailsResponse {
    String department;
    String subject;
    int semester; 
}
