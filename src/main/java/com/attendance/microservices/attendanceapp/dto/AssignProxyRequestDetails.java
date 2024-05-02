package com.attendance.microservices.attendanceapp.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssignProxyRequestDetails {
    private List<TeachersListDTO> teachers;
    private List<SubjectsListDTO> subjects;
}
