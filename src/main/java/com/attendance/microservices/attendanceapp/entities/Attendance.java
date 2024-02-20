package com.attendance.microservices.attendanceapp.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "attendance")
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "roll_number", referencedColumnName = "roll_number")
    private Students student;

    @ManyToOne
    @JoinColumn(name = "subject_id", referencedColumnName = "id")
    private Subjects subject;

    private String date;

    private boolean present;

    private boolean proxy;

}
