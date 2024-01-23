package com.attendance.microservices.attendanceapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.attendance.microservices.attendanceapp.entities.SubjectTeachers;

public interface SubjectTeachersRepository extends JpaRepository<SubjectTeachers, Integer> {
    List<SubjectTeachers> findAllByTeacherId(int id);
}
