package com.attendance.microservices.attendanceapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.attendance.microservices.attendanceapp.entities.IdRoll;


public interface IdRollRepository extends JpaRepository<IdRoll, String> {
    IdRoll findFirstByCardNumber(String cardNumber);
}
