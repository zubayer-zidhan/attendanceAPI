package com.attendance.microservices.attendanceapp.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.attendance.microservices.attendanceapp.dto.AttendanceDataDTO;
import com.attendance.microservices.attendanceapp.dto.AttendanceDetailsSubjectResponse;
import com.attendance.microservices.attendanceapp.dto.AttendanceRecordRequestDTO;
import com.attendance.microservices.attendanceapp.dto.AttendanceSubjectDetails;
import com.attendance.microservices.attendanceapp.entities.Attendance;
import com.attendance.microservices.attendanceapp.entities.StudentSubjects;
import com.attendance.microservices.attendanceapp.entities.Students;
import com.attendance.microservices.attendanceapp.entities.Subjects;
import com.attendance.microservices.attendanceapp.repository.AttendanceRepository;
import com.attendance.microservices.attendanceapp.repository.StudentSubjectsRepository;
import com.attendance.microservices.attendanceapp.repository.StudentsRepository;
import com.attendance.microservices.attendanceapp.repository.SubjectsRepository;
import com.attendance.microservices.attendanceapp.services.AttendanceService;

@Service
public class AttendenceServiceImpl implements AttendanceService {

    private boolean takingAttendance = false;
    private AttendanceSubjectDetails subjectContext;
    private Map<String, Long> recentRollNumbers = new ConcurrentHashMap<>();

    @Autowired
    AttendanceRepository attendanceRepository;

    @Autowired
    StudentsRepository studentsRepository;

    @Autowired
    SubjectsRepository subjectsRepository;

    @Autowired
    StudentSubjectsRepository studentSubjectsRepository;

    // Start taking attendance, i.e. set "takingAttendance" = true
    @Override
    public void startTakingAttendance() {
        this.takingAttendance = true;
    }

    // Set the subject context while taking attendance
    @Override
    public void setSubjectContext(AttendanceSubjectDetails subjectDetails) {
        this.subjectContext = subjectDetails;
    }

    // Get All attendance details of all students
    @Override
    public List<Attendance> getAttendanceDetails() {
        return attendanceRepository.findAll();
    }

    @Override
    public AttendanceSubjectDetails getSubjectContext() {
        return this.subjectContext;
    }

    @Override
    public boolean getTakingAttendance() {
        return this.takingAttendance;
    }

    // Method to validate the request and prevent duplicate submissions
    public boolean isDuplicateRequest(String rollNumber) {
        // Check if the roll number exists in the cache
        if (recentRollNumbers.containsKey(rollNumber)) {
            // If the roll number exists, check the time difference
            long previousTime = recentRollNumbers.get(rollNumber);
            long currentTime = System.currentTimeMillis();
            long elapsedTime = currentTime - previousTime;

            // Define your threshold for duplicate submissions(here, 30 mins)
            long threshold = TimeUnit.MINUTES.toMillis(30);

            // If the time difference is within the threshold, consider it a duplicate
            // request
            if (elapsedTime <= threshold) {
                return true; // Duplicate request
            }
        }

        // If the roll number is not in the cache or the time difference exceeds the
        // threshold, it's not a duplicate request
        // Update the cache with the current timestamp
        recentRollNumbers.put(rollNumber, System.currentTimeMillis());
        return false;
    }

    // Method to clean up the cache
    public void clearCache() {
        recentRollNumbers.clear();
    }

    // Mark the remainder of students as absent
    private void markAbsentForCurrentDate(String subjectID, String currentDate, boolean proxy) {

        // Retrieve attendance records for the current date
        List<Attendance> attendanceRecords = attendanceRepository.findAllBySubjectIdAndDate(subjectID, currentDate);

        // Retrieve all subjects (assuming you have a method to fetch all subjects)
        List<StudentSubjects> studentSubjectsList = studentSubjectsRepository.findAllBySubjectId(subjectID);

        // Get the roll numbers of students who are present for this subject on the
        // current date
        List<String> presentRollNumbers = attendanceRecords.stream()
                .map(attendance -> attendance.getStudent().getRollNumber())
                .collect(Collectors.toList());

        // Mark students as absent if their roll number is not in the list of present
        // roll numbers
        for (StudentSubjects studentSubject : studentSubjectsList) {
            if (!presentRollNumbers.contains(studentSubject.getStudent().getRollNumber())) {
                // Create new attendance record for absent student
                Attendance absentAttendance = Attendance.builder()
                        .student(studentSubject.getStudent())
                        .subject(studentSubject.getSubject())
                        .date(currentDate)
                        .present(false)
                        .proxy(proxy)
                        .build();

                // TODO: Uncomment to save absent record
                // Save the attendance record
                // attendanceRepository.save(absentAttendance);
                System.out.println("Absent: " + absentAttendance);
            }
        }
    }

    // Convert idCard(barcode ID) to student roll number
    // TODO: Implement the converter
    private String convertIdToRoll(String id) {
        return id;
    }

    // Get Attendance Data by Subject
    @Override
    public List<AttendanceDetailsSubjectResponse> getAttendanceDetailsBySubjectId(String subjectId) {

        List<Attendance> attendanceList = attendanceRepository.findAllBySubjectIdOrderByStudentRollNumber(subjectId);

        Map<String, AttendanceDetailsSubjectResponse> studentAttendanceMap = new HashMap<>();

        for (Attendance attendance : attendanceList) {
            // Check if the student is already in the map
            if (studentAttendanceMap.containsKey(attendance.getStudent().getRollNumber())) {
                // If yes, add the attendance data to the existing entry
                AttendanceDetailsSubjectResponse existingDetails = studentAttendanceMap
                        .get(attendance.getStudent().getRollNumber());
                AttendanceDataDTO tempDTO = AttendanceDataDTO.builder()
                        .date(attendance.getDate())
                        .present(attendance.isPresent())
                        .build();

                existingDetails.getAttendance().add(tempDTO);
            } else {
                // If no, create a new entry for the student
                List<AttendanceDataDTO> tempList = new ArrayList<>();
                AttendanceDataDTO tempDTO = AttendanceDataDTO.builder()
                        .date(attendance.getDate())
                        .present(attendance.isPresent())
                        .build();
                tempList.add(tempDTO);

                AttendanceDetailsSubjectResponse temp = AttendanceDetailsSubjectResponse.builder()
                        .name(attendance.getStudent().getName())
                        .attendance(tempList)
                        .rollNumber(attendance.getStudent().getRollNumber())
                        .build();

                studentAttendanceMap.put(attendance.getStudent().getRollNumber(), temp);
            }
        }

        // Convert the values of the map to a list
        List<AttendanceDetailsSubjectResponse> attendanceDetails = new ArrayList<>(studentAttendanceMap.values());

        return attendanceDetails;
    }

    // Get Attendance Data by Subject and Date
    @Override
    public List<AttendanceDetailsSubjectResponse> getAttendanceDetailsBySubjectIdAndDate(String subjectId,
            String date) {

        List<Attendance> attendanceList = attendanceRepository.findAllBySubjectIdAndDate(subjectId, date);

        Map<String, AttendanceDetailsSubjectResponse> studentAttendanceMap = new HashMap<>();

        for (Attendance attendance : attendanceList) {
            // Check if the student is already in the map
            if (studentAttendanceMap.containsKey(attendance.getStudent().getRollNumber())) {
                // If yes, add the attendance data to the existing entry
                AttendanceDetailsSubjectResponse existingDetails = studentAttendanceMap
                        .get(attendance.getStudent().getRollNumber());
                AttendanceDataDTO tempDTO = AttendanceDataDTO.builder()
                        .date(attendance.getDate())
                        .present(attendance.isPresent())
                        .build();

                existingDetails.getAttendance().add(tempDTO);
            } else {
                // If no, create a new entry for the student
                List<AttendanceDataDTO> tempList = new ArrayList<>();
                AttendanceDataDTO tempDTO = AttendanceDataDTO.builder()
                        .date(attendance.getDate())
                        .present(attendance.isPresent())
                        .build();
                tempList.add(tempDTO);

                AttendanceDetailsSubjectResponse temp = AttendanceDetailsSubjectResponse.builder()
                        .name(attendance.getStudent().getName())
                        .attendance(tempList)
                        .rollNumber(attendance.getStudent().getRollNumber())
                        .build();

                studentAttendanceMap.put(attendance.getStudent().getRollNumber(), temp);
            }
        }

        // Convert the values of the map to a list
        List<AttendanceDetailsSubjectResponse> attendanceDetails = new ArrayList<>(studentAttendanceMap.values());

        return attendanceDetails;
    }

    // Process Incoming IDs from the IoT device
    @Override
    public ResponseEntity<String> processIncomingIds(AttendanceRecordRequestDTO request) {

        // Check if subjectContext is not set, or, not taking attendance
        if (!takingAttendance || (subjectContext == null)) {
            System.out.println(takingAttendance + ", " + request.getStudentID());
            System.out.println("Either not taking attendance or subject context not sent");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Either not taking attendance or subject context not sent");

        }

        // Check if request already processed for this rollNumber from cache
        if (isDuplicateRequest(request.getStudentID())) {
            System.out.println("Duplicate Request for: " + request.getStudentID());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Duplicate attendance request");
        }


        // Associate studentIds with subject details and update the attendance table
        String rollNumber = convertIdToRoll(request.getStudentID());
        publishAttendance(subjectContext, rollNumber);

        return ResponseEntity.ok("Attendance processed successfully.");

    }

    // Process attendance for one student with subject context
    private void publishAttendance(AttendanceSubjectDetails subjectDetails, String rollNumber) {

        String subjectID = subjectDetails.getSubjectID();
        String date = subjectDetails.getDate();
        boolean proxy = subjectDetails.isProxy();

        // System.out.println("Subject: " + subjectID);
        // System.out.println("Date: " + date);
        // System.out.println("Student Roll Number: " + rollNumber);
        // System.out.println("Proxy: " + proxy);

        Students currentStudent = studentsRepository.findFirstByRollNumber(rollNumber);
        Subjects currentSubject = subjectsRepository.findFirstById(subjectID);

        // Implement the logic to update the attendance table
        Attendance newAttendance = Attendance.builder()
                .subject(currentSubject)
                .student(currentStudent)
                .date(date)
                .present(true)
                .proxy(proxy)
                .build();

        // TODO: Uncomment to save attendance record to database
        // attendanceRepository.save(newAttendance);
        System.out.println(newAttendance);
    }

    // On "STOP", stop taking attendance
    @Override
    public void stopTakingAttendance() {
        // Stop taking attendance
        if (takingAttendance) {
            markAbsentForCurrentDate(this.subjectContext.getSubjectID(), this.subjectContext.getDate(),
                    this.getSubjectContext().isProxy());

            this.takingAttendance = false;
            this.subjectContext = null;

            // Clear the cache when endAttendance is called, or, when 30mins over
            clearCache();
        }
    }

    // If "STOP" attendance not sent, stop taking attendance automatically, check if
    // taking attendance, if taking
    // then only "STOP"
    @Override
    @Scheduled(fixedDelay = 1800000) // 30 minutes in milliseconds
    public void stopTakingAttendanceAutomatically() {
        stopTakingAttendance();
    }

}
