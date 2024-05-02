package com.attendance.microservices.attendanceapp.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.attendance.microservices.attendanceapp.dto.AssignProxyDTO;
import com.attendance.microservices.attendanceapp.dto.AssignProxyRequestDetails;
import com.attendance.microservices.attendanceapp.dto.SubjectsListDTO;
import com.attendance.microservices.attendanceapp.dto.TeachersListDTO;
import com.attendance.microservices.attendanceapp.entities.Departments;
import com.attendance.microservices.attendanceapp.entities.SubjectTeachers;
import com.attendance.microservices.attendanceapp.entities.Subjects;
import com.attendance.microservices.attendanceapp.entities.SubstituteAssignments;
import com.attendance.microservices.attendanceapp.entities.Teachers;
import com.attendance.microservices.attendanceapp.repository.DepartmentsRepository;
import com.attendance.microservices.attendanceapp.repository.SubjectTeachersRepository;
import com.attendance.microservices.attendanceapp.repository.SubjectsRepository;
import com.attendance.microservices.attendanceapp.repository.TeachersRepository;
import com.attendance.microservices.attendanceapp.services.DepartmentService;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    DepartmentsRepository departmentsRepository;

    @Autowired
    TeachersRepository teachersRepository;

    @Autowired
    SubjectTeachersRepository subjectTeachersRepository;

    @Autowired
    SubjectsRepository subjectsRepository;

    // Find all departments
    @Override
    public List<Departments> getDepartmentDetails() {
        return departmentsRepository.findAll();
    }

    // Find all teachers in department, and subjects of given teacher
    @Override
    public AssignProxyRequestDetails getTeachersAndSubjects(String username) {
        // Get the teacher making the request
        Teachers currTeacher = teachersRepository.findFirstByUserUsername(username);

        if (currTeacher == null) {
            return null;
        } else {

            // Find all teachers belonging to same department
            List<Teachers> allTeachersList = teachersRepository.findAllByDeptId(currTeacher.getDeptId());

            // Loop over all teachers in teachersList and add them to DTO
            List<TeachersListDTO> teachersListDTOs = new ArrayList<>();

            for (Teachers teacher : allTeachersList) {
                TeachersListDTO tempResponse = TeachersListDTO.builder()
                        .name(teacher.getName())
                        .id(teacher.getId())
                        .build();

                        teachersListDTOs.add(tempResponse);
            }

            
            // Find all subjects for given teacher
            List<SubjectTeachers> subjectsList = subjectTeachersRepository.findAllByTeacherId(currTeacher.getId());

            // Loop over all subjects and add to allSubjectsList
            List<SubjectsListDTO> allSubjectsList = new ArrayList<>();

            // Iterate over all entries from "subject_teachers"
            for (SubjectTeachers subject : subjectsList) {
                SubjectsListDTO tempResponse = SubjectsListDTO.builder()
                        .name(subject.getSubject().getName())
                        .id(subject.getSubject().getId())
                        .build();

                allSubjectsList.add(tempResponse);
            }

            // Add allSubjects and allTeachers to allDetails
            AssignProxyRequestDetails allDetails = AssignProxyRequestDetails.builder()
                                                    .subjects(allSubjectsList)
                                                    .teachers(teachersListDTOs)
                                                    .build();
            
            return allDetails;
        }
    }


    // Assign proxy to a particular teacher
    @Override
    public ResponseEntity<String> assignProxy(AssignProxyDTO request) {

        Subjects subject = subjectsRepository.findById(request.getSubjectId()).orElse(null);
        Teachers teacher = teachersRepository.findById(request.getSubstituteTeacherId()).orElse(null);

        if(subject == null || teacher == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid subjectId or teacherId");
        }

        SubstituteAssignments newAssignment = SubstituteAssignments.builder()
                                                .subject(subject)
                                                .teacher(teacher)
                                                .date(request.getDate())
                                                .build();


        System.out.println("PROXY: " + newAssignment);
        return ResponseEntity.ok("Added Proxy assignment for teacher: " + teacher.getName() +", and, subject: " + subject.getName());
    }



    
    
}
