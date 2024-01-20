package com.attendance.microservices.attendanceapp.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "subject_teachers")
public class SubjectTeachers{
    @Id
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "subject_id", referencedColumnName = "id")
    private Subjects subject;
    
    @ManyToOne
    @JoinColumn(name = "teacher_id", referencedColumnName = "id")
    private Teachers teacher;

}
