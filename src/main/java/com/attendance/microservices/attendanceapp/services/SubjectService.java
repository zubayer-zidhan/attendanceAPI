package com.attendance.microservices.attendanceapp.services;

import java.util.List;

import com.attendance.microservices.attendanceapp.entities.Subjects;

public interface SubjectService {
    public List<Subjects> getSubjectDetails();
}
