package edu.hogwarts.services;

import edu.hogwarts.StudentAdminApplication.CourseNotFoundException;
import edu.hogwarts.models.Course;
import edu.hogwarts.models.Teacher;
import edu.hogwarts.models.dto.StudentDTO;
import edu.hogwarts.repositories.CourseRepository;
import edu.hogwarts.repositories.TeacherRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import edu.hogwarts.repositories.StudentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository, TeacherRepository teacherRepository, StudentRepository studentRepository) {
        this.courseRepository = courseRepository;
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Optional<Course> getCourseById(int id) {
        Long longId = Long.valueOf(id); // Convert int to Long
        return courseRepository.findById(longId);
    }

    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    public Course updateCourse(int id, Course course) {
        Long longId = Long.valueOf(id); // Convert int to Long
        Optional<Course> existingCourseOptional = courseRepository.findById(longId);
        if (existingCourseOptional.isPresent()) {
            Course existingCourse = existingCourseOptional.get();

            existingCourse.setSubject(course.getSubject());
            existingCourse.setSchoolYear(course.getSchoolYear());
            existingCourse.setCurrent(course.isCurrent());
            existingCourse.setTeachers(course.getTeachers());

            // No need to convert students to DTOs here, just set them directly
            existingCourse.setStudents(course.getStudents());

            return courseRepository.save(existingCourse);
        } else {
            throw new RuntimeException("Course with ID " + id + " not found");
        }
    }

    public void deleteCourse(int id) {
        Long longId = Long.valueOf(id);
        if (courseRepository.existsById(longId)) {
            courseRepository.deleteById(longId);
        } else {
            throw new RuntimeException("Course with ID " + id + " not found");
        }
    }



    public Teacher getTeacherByCourseId(int id) {
        Optional<Course> courseOptional = courseRepository.findById((long) id);
        if (courseOptional.isPresent()) {
            List<Teacher> teachers = teacherRepository.findByCoursesId(id);
            if (!teachers.isEmpty()) {
                return teachers.get(0);
            } else {
                return null;
            }
        } else {
            throw new CourseNotFoundException("Course with ID " + id + " not found");
        }
    }










    public void setTeacherForCourse(int id, Teacher teacher) {
        Long longId = Long.valueOf(id);
        Course course = courseRepository.findById(longId)
                .orElseThrow(() -> new RuntimeException("Course with ID " + id + " not found"));

        List<Teacher> teachers = new ArrayList<>();
        teachers.add(teacher);

        course.setTeachers(teachers);
        courseRepository.save(course);
    }

    public List<StudentDTO> getStudentsByCourseId(int id) {
        Long longId = Long.valueOf(id);
        Optional<Course> courseOptional = courseRepository.findById(longId);
        if (courseOptional.isPresent()) {
            Course course = courseOptional.get();

            return course.getStudents().stream()
                    .map(this::mapToDTO)
                    .collect(Collectors.toList());
        } else {
            throw new RuntimeException("Course with ID " + id + " not found");
        }
    }


    private StudentDTO mapToDTO(StudentDTO student) {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setId(student.getId());
        studentDTO.setFirstName(student.getFirstName());
        studentDTO.setMiddleName(student.getMiddleName());
        studentDTO.setLastName(student.getLastName());
        // Map other properties as needed
        return studentDTO;
    }

    public void removeTeacherFromCourse(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course with ID " + courseId + " not found"));

        course.setTeachers(null);
        courseRepository.save(course);
    }


    public void removeStudentFromCourse(Long courseId, int studentId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course with ID " + courseId + " not found"));

        List<StudentDTO> students = course.getStudents();
        students.removeIf(student -> student.getId() == studentId); // Remove the student with the specified ID

        courseRepository.save(course);
    }


    public void addStudentToCourse(Long courseId, Long studentId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course with ID " + courseId + " not found"));


        StudentDTO student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student with ID " + studentId + " not found"));


        List<StudentDTO> students = course.getStudents();
        students.add(student);
        course.setStudents(students);


        courseRepository.save(course);
    }

    public void addStudentsToCourse(int courseId, List<StudentDTO> studentDTOs) {

        Course course = courseRepository.findById((long) courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + courseId));


        for (StudentDTO studentDTO : studentDTOs) {

            StudentDTO student = studentDTO.toStudent();
            course.addStudent(student);
        }


        courseRepository.save(course);
    }


    public void addTeachersToCourse(Long courseId, List<Teacher> teachers) {

        Optional<Course> optionalCourse = courseRepository.findById(courseId);

        if (optionalCourse.isPresent()) {
            Course course = optionalCourse.get();


            for (Teacher teacher : teachers) {

                if (!course.getTeachers().contains(teacher)) {
                    course.getTeachers().add(teacher);
                    teacher.getCourses().add(course);
                }
            }


            courseRepository.save(course);
        } else throw new CourseNotFoundException("Kursus med ID " + courseId + " blev ikke fundet");
    }


}
