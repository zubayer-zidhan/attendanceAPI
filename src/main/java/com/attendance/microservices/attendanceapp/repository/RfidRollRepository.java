package com.attendance.microservices.attendanceapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.attendance.microservices.attendanceapp.entities.RfidRoll;


public interface RfidRollRepository extends JpaRepository<RfidRoll, String> {
    RfidRoll findFirstByRfidNumber(String rfidNumber);
}
