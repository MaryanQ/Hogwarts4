package edu.hogwarts.services;

import edu.hogwarts.models.Teacher;
import edu.hogwarts.repositories.TeacherRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;

    @Autowired
    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    public Optional<Teacher> getTeacherById(int id) {
        Long longId = Long.valueOf(id); // Convert int to Long
        return teacherRepository.findById(longId);
    }

    public Teacher createTeacher(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    public Teacher updateTeacher(int id, Teacher teacher) {
        Long longId = Long.valueOf(id); // Convert int to Long
        Optional<Teacher> existingTeacherOptional = teacherRepository.findById(longId);
        if (existingTeacherOptional.isPresent()) {
            Teacher existingTeacher = existingTeacherOptional.get();
            existingTeacher.setFirstName(teacher.getFirstName());
            existingTeacher.setMiddleName(teacher.getMiddleName());
            existingTeacher.setLastName(teacher.getLastName());
            existingTeacher.setDateOfBirth(teacher.getDateOfBirth());
            existingTeacher.setHouse(teacher.getHouse());
            existingTeacher.setHeadOfHouse(teacher.isHeadOfHouse());
            existingTeacher.setEmployment(teacher.getEmployment());
            existingTeacher.setEmploymentStart(teacher.getEmploymentStart());
            existingTeacher.setEmploymentEnd(teacher.getEmploymentEnd());
            return teacherRepository.save(existingTeacher);
        } else {
            throw new RuntimeException("Teacher with ID " + id + " not found");
        }
    }

    public void deleteTeacher(int id) {
        Long longId = Long.valueOf(id); // Convert int to Long
        if (teacherRepository.existsById(longId)) {
            teacherRepository.deleteById(longId);
        } else {
            throw new RuntimeException("Teacher with ID " + id + " not found");
        }
    }

    public Teacher patchTeacher(int id, Teacher teacherUpdates) {
        Long longId = Long.valueOf(id); // Convert int to Long
        Optional<Teacher> optionalTeacher = teacherRepository.findById(longId);

        if (optionalTeacher.isPresent()) {
            Teacher existingTeacher = optionalTeacher.get();

            // Update headOfHouse if not null in teacherUpdates
            if (teacherUpdates.isHeadOfHouse()) {
                existingTeacher.setHeadOfHouse(teacherUpdates.isHeadOfHouse());
            }

            // Update employmentEnd if not null in teacherUpdates
            if (teacherUpdates.getEmploymentEnd() != null) {
                existingTeacher.setEmploymentEnd(teacherUpdates.getEmploymentEnd());
            }

            // Update employment if not null in teacherUpdates
            if (teacherUpdates.getEmployment() != null) {
                existingTeacher.setEmployment(teacherUpdates.getEmployment());
            }

            // Save and return the updated teacher
            return teacherRepository.save(existingTeacher);
        } else {
            throw new EntityNotFoundException("Teacher with ID " + id + " not found");
        }
    }
}
