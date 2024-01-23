package com.attendance.microservices.attendanceapp.services.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.attendance.microservices.attendanceapp.dto.TeacherDetailsResponse;
import com.attendance.microservices.attendanceapp.entities.SubjectTeachers;
import com.attendance.microservices.attendanceapp.entities.SubstituteAssignments;
import com.attendance.microservices.attendanceapp.entities.Teachers;
import com.attendance.microservices.attendanceapp.repository.SubjectTeachersRepository;
import com.attendance.microservices.attendanceapp.repository.SubstituteAssignmentsRepository;
import com.attendance.microservices.attendanceapp.repository.TeachersRepository;
import com.attendance.microservices.attendanceapp.services.TeacherService;

@Service
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    TeachersRepository teachersRepository;

    @Autowired
    SubjectTeachersRepository subjectTeachersRepository;

    @Autowired
    SubstituteAssignmentsRepository substituteAssignmentsRepository;

    @Override
    public List<TeacherDetailsResponse> getTeacherSubjects(String username) {

        Teachers teacher = teachersRepository.findFirstByUserUsername(username);

        if (teacher == null) {
            return null;
        } else {

            // Get teacherID which will be used to search in "subject_teachers" and "substitute_assignments"
            int teacherID = teacher.getId();

            // Get today's date and format it in "yyyy-MM-dd" format, for searching in "substitute_assignments"
            String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            // Get all entries for the required teacherID
            List<SubjectTeachers> subjectsList = subjectTeachersRepository.findAllByTeacherId(teacherID);
            List<SubstituteAssignments> substituteList = substituteAssignmentsRepository.findAllByTeacherIdAndDate(teacherID, currentDate);

            // Create a new empty response
            List<TeacherDetailsResponse> teacherDetails = new ArrayList<>();


            // Iterate over all entries from "subject_teachers"
            for (SubjectTeachers subject : subjectsList) {
                TeacherDetailsResponse tempResponse = TeacherDetailsResponse.builder()
                        .department(subject.getSubject().getDepartment().getName())
                        .semester(subject.getSubject().getSemester())
                        .subject(subject.getSubject().getName())
                        .subjectId(subject.getSubject().getId())
                        .build();

                teacherDetails.add(tempResponse);
            }

            // Iterate over all entries from "substitute_assignments"
            for (SubstituteAssignments subject : substituteList) {
                TeacherDetailsResponse tempResponse = TeacherDetailsResponse.builder()
                        .department(subject.getSubject().getDepartment().getName())
                        .semester(subject.getSubject().getSemester())
                        .subject(subject.getSubject().getName())
                        .subjectId(subject.getSubject().getId())
                        .build();

                teacherDetails.add(tempResponse);
            }

            return teacherDetails;
        }
    }
}
