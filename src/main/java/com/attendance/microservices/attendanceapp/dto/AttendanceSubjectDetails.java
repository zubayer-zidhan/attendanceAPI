package com.attendance.microservices.attendanceapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceSubjectDetails {
   String subjectID;
   String teacherID;
   String date;
}
