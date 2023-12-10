package com.attendance.microservices.attendanceapp.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.attendance.microservices.attendanceapp.dto.TeacherDetailsResponse;
import com.attendance.microservices.attendanceapp.entities.Subjects;
import com.attendance.microservices.attendanceapp.entities.Teachers;
import com.attendance.microservices.attendanceapp.repository.DepartmentsRepository;
import com.attendance.microservices.attendanceapp.repository.SubjectsRepository;
import com.attendance.microservices.attendanceapp.repository.TeachersRepository;
import com.attendance.microservices.attendanceapp.services.TeacherService;

@Service
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    TeachersRepository teachersRepository;

    @Autowired
    SubjectsRepository subjectsRepository;

    @Autowired
    DepartmentsRepository departmentsRepository;

    @Override
    public List<TeacherDetailsResponse> getTeacherDetails(String username) {

        Teachers teacher = teachersRepository.findFirstByUserUsername(username);

        if (teacher == null) {
            return null;
        } else {

            List<Subjects> subjectsList = subjectsRepository.findAllByTeacherId(teacher.getId());
            List<TeacherDetailsResponse> teacherDetails = new ArrayList<>();


            for(Subjects subject : subjectsList) {
                TeacherDetailsResponse tempResponse = TeacherDetailsResponse.builder()
                    .department(subject.getDepartment().getName())
                    .semester(subject.getSemester())
                    .subject(subject.getName())
                    .subjectId(subject.getId())
                    .build();
                
                teacherDetails.add(tempResponse);
            }

            return teacherDetails;


        }
    }
}
