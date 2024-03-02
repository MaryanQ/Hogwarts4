package edu.hogwarts.controllers;

import edu.hogwarts.models.Course;
import edu.hogwarts.models.Teacher;
import edu.hogwarts.models.dto.StudentDTO;
import edu.hogwarts.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/course")
public  class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("")
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }


    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long id) {
        Optional<Course> courseOptional = courseService.getCourseById(id.intValue());

        if (courseOptional.isPresent()) {
            Course course = courseOptional.get();

            // Populate teacher information
            List<Teacher> teachers = new ArrayList<>();
            Teacher teacher = courseService.getTeacherByCourseId(id.intValue());
            if (teacher != null) {
                teachers.add(teacher);
            }
            course.setTeachers(teachers);

            // Populate students information
            List<StudentDTO> students = courseService.getStudentsByCourseId(id.intValue());
            course.setStudents(students);

            return ResponseEntity.ok(course);
        } else {
            return ResponseEntity.notFound().build();
        }
    }




    @GetMapping("/{id}/teacher") // Fixed endpoint mapping
    public ResponseEntity<Object> getTeacherByCourseId(@PathVariable Long id) {
        Teacher teacher = courseService.getTeacherByCourseId(id.intValue());
        if (teacher != null) {
            return ResponseEntity.ok(teacher);
        } else {
            return ResponseEntity.notFound().build(); // Return 404 if the teacher is not found
        }
    }


    @PostMapping("/courses/{courseId}/teacher")
    public ResponseEntity<String> addTeachersToCourse(@PathVariable int courseId, @RequestBody List<Teacher> teachers) {
        try {
            courseService.addTeachersToCourse((long) courseId, teachers);
            return ResponseEntity.ok("Lærerne blev tilføjet til kurset");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Fejl: Kunne ikke tilføje lærerne til kurset");
        }
    }

    @GetMapping("/{id}/students")
    public List<StudentDTO> getStudentsDTOByCourseId(@PathVariable int id) {
        return courseService.getStudentsByCourseId(id);
    }


    @PostMapping
    public Course createCourse(@RequestBody Course course) {
        return courseService.createCourse(course);
    }

    // Endpoint for at tilføje studerende til et kursus
    @PostMapping("/{courseId}/students")
    public ResponseEntity<String> addStudentsToCourse(@PathVariable int courseId, @RequestBody List<StudentDTO> students) {
        courseService.addStudentsToCourse(courseId, students);
        return ResponseEntity.ok("Studerende blev tilføjet til kurset");
    }



    @PutMapping("/{id}")
    public Course updateCourse(@PathVariable int id, @RequestBody Course course) {
        return this.courseService.updateCourse(id, course);
    }

    @PutMapping("/{id}/teacher")
    public void setTeacherForCourse(@PathVariable int id, @RequestBody Teacher teacher) {
        courseService.setTeacherForCourse(id, teacher);
    }

    @PutMapping("/{id}/students/{studentId}")
    public void addStudentToCourse(@PathVariable int id, @PathVariable String studentId) {
        // Convert the studentId from String to Long
        long parsedStudentId;
        try {
            parsedStudentId = Long.parseLong(studentId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid student ID format: " + studentId);
        }
        int parsedStudentIdInt = Math.toIntExact(parsedStudentId);
        courseService.addStudentToCourse((long) id, (long) parsedStudentIdInt);



    }

    @DeleteMapping("/{id}")
    public void deleteCourse(@PathVariable int id) {
        courseService.deleteCourse(id);
    }

    @DeleteMapping("/{id}/teacher")
    public void removeTeacherFromCourse(@PathVariable int id) {
        courseService.removeTeacherFromCourse(Long.valueOf(id));
    }

    @DeleteMapping("/{id}/students/{studentId}")
    public void removeStudentFromCourse(@PathVariable Long id, @PathVariable Long studentId) {
        int studentIdInt = studentId.intValue(); // Convert Long to int
        courseService.removeStudentFromCourse(id, studentIdInt);
    }

    // Exception handler for parsing errors
    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<String> handleNumberFormatException(NumberFormatException ex) {
        return ResponseEntity.badRequest().body("Invalid student ID format: " + ex.getMessage());
    }
}


