package com.attendance.microservices.attendanceapp.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
@Table(name = "rfid_roll")
public class RfidRoll{
    @Id
    @Column(name = "roll_number", unique = true, nullable = false)
    private String rollNumber;

    @OneToOne
    @JoinColumn(name = "roll_number", referencedColumnName = "roll_number")
    private Students student;

    @Column(name = "rfid_number", unique = true, nullable = false)
    private String rfidNumber;
}
