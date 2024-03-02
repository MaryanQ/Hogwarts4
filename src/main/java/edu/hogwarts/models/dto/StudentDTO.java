package edu.hogwarts.models.dto;


import edu.hogwarts.models.House;
import jakarta.persistence.*;

import java.time.LocalDate;



@Entity
public class StudentDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String firstName;
    private String middleName;
    private String lastName;
    private LocalDate dateOfBirth;

    @ManyToOne
    @JoinColumn(name = "house_id")
    private House house; // Reference til House-klassen

    private boolean prefect;
    private int schoolYear;
    private int enrollmentYear;
    private Integer graduationYear;




    public String getHouseName() {
        if (house != null) {
            return house.getName();
        } else {
            return null;
        }
    }


    public StudentDTO() {
    }

    public StudentDTO(int id, String firstName, String middleName, String lastName, LocalDate dateOfBirth, House house, boolean prefect, int schoolYear, int enrollmentYear, Integer graduationYear) {
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.house = house;
        this.prefect = prefect;
        this.schoolYear = schoolYear;
        this.enrollmentYear = enrollmentYear;
        this.graduationYear = graduationYear;
    }


    public StudentDTO toStudent() {
        StudentDTO student = new StudentDTO();
        student.setId(this.getId());
        student.setFirstName(this.getFirstName());
        student.setMiddleName(this.getMiddleName());
        student.setLastName(this.getLastName());
        student.setDateOfBirth(this.getDateOfBirth());
        student.setHouse(this.getHouse());
        student.setPrefect(this.isPrefect());
        student.setSchoolYear(this.getSchoolYear());
        student.setEnrollmentYear(this.getEnrollmentYear());
        student.setGraduationYear(this.isGraduationYear());
        return student;
    }

    // Getters and setters...

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    public boolean isPrefect() {
        return prefect;
    }

    public void setPrefect(boolean prefect) {
        this.prefect = prefect;
    }


    public int getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(int schoolYear) {
        this.schoolYear = schoolYear;
    }


    public int getEnrollmentYear() {
        return enrollmentYear;
    }

    public void setEnrollmentYear(int enrollmentYear) {
        this.enrollmentYear = enrollmentYear;
    }

    public Integer isGraduationYear() {
        return graduationYear;
    }

    public void setGraduationYear(Integer graduationYear) {
        this.graduationYear = graduationYear;
    }


}
