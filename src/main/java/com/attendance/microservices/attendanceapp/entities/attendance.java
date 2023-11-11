package com.attendance.microservices.attendanceapp.entities;


import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class attendance {
    private String rollNo;
    private int subjectId;
    private String date;
    private boolean present;

}
