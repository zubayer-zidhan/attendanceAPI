package com.attendance.microservices.attendanceapp.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class subjects {
    @Id
    private String id;

    private String name;
    private int deptId;
    private int semester;
    private int teacherId;
}
