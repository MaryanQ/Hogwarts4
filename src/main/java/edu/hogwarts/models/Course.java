package edu.hogwarts.models;

import edu.hogwarts.models.dto.StudentDTO;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String subject;
    private int schoolYear;
    private boolean current;


    @OneToMany
    private List<StudentDTO> students;

    @ManyToMany(mappedBy = "courses")
    private List<Teacher> teachers = new ArrayList<>();

    // Getter and setter for teachers
    public List<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }






    // Constructors
    public Course() {
    }

    public Course(String subject, int schoolYear, boolean current) {
        this.subject = subject;
        this.schoolYear = schoolYear;
        this.current = current;

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(int schoolYear) {
        this.schoolYear = schoolYear;
    }

    public boolean isCurrent() {
        return current;
    }

    public void setCurrent(boolean current) {
        this.current = current;
    }





    public List<StudentDTO> getStudents() {
        return students;
    }


    public void setStudents(List<StudentDTO> students) {
        this.students = students;
    }

    public void addStudent(StudentDTO student) {
        if (students == null) {
            students = new ArrayList<>();
        }
        students.add(student);
    }

}


