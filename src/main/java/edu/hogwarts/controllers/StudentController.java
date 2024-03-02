package edu.hogwarts.controllers;

import edu.hogwarts.models.House;
import edu.hogwarts.models.dto.StudentDTO;
import edu.hogwarts.services.HouseService;
import edu.hogwarts.services.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;
    private final HouseService houseService;

    public StudentController(StudentService studentService, HouseService houseService) {
        this.studentService = studentService;
        this.houseService = houseService;
    }

    @GetMapping("")
    public ResponseEntity<List<StudentDTO>> getAllStudents() {
        List<StudentDTO> allStudents = studentService.getAllStudents();
        return ResponseEntity.ok(allStudents);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable int id) {
        StudentDTO studentDTO = studentService.getStudentDTOById(id).orElse(null);
        if (studentDTO != null) {
            return ResponseEntity.ok(studentDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }



    @PostMapping("/create")
    public ResponseEntity<StudentDTO> createStudent(@RequestBody StudentDTO studentDTO) {
        House house = studentDTO.getHouse();
        if (house == null) {
            return ResponseEntity.badRequest().build();
        }

        StudentDTO createdStudent = studentService.createStudent(studentDTO);
        return new ResponseEntity<>(createdStudent, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<StudentDTO> updateStudent(@PathVariable int id, @RequestBody StudentDTO studentDTO) {
        StudentDTO updatedStudentDTO = studentService.updateStudent(id, studentDTO);
        if (updatedStudentDTO != null) {
            return ResponseEntity.ok(updatedStudentDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable int id) {
        int studentId = Long.valueOf(id).intValue(); // Convert Long to int
        studentService.deleteStudent(studentId);
        return ResponseEntity.noContent().build();
    }




}






