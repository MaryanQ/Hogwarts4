package edu.hogwarts.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.hogwarts.models.House;
import edu.hogwarts.repositories.HouseRepository;
import edu.hogwarts.models.dto.StudentDTO;
import edu.hogwarts.repositories.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private HouseService houseService; // Husk at injicere HouseService


    @Autowired
    private StudentRepository studentRepository; //

    @Autowired
    private HouseRepository houseRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Transactional
    public void createOrUpdateStudent(StudentDTO studentDTO) {
        // Step 1: Map StudentDTO to Student entity
        StudentDTO student = new StudentDTO();
        student.setFirstName(studentDTO.getFirstName()); // Assuming there's a name field in StudentDTO
        // Set other fields as needed

        // Step 2: Save the House entity
        House savedHouse = houseRepository.save(studentDTO.getHouse());

        // Step 3: Set the House reference in the Student entity
        student.setHouse(savedHouse);

        // Step 4: Save the Student entity
        studentRepository.save(student);
    }


    public StudentDTO saveAStudent(StudentDTO student) {
        return studentRepository.save(student);
    }

    public Optional<StudentDTO> getStudentById(Long id) {
        return studentRepository.findById(id);
    }


    public void processAndSaveStudent(StudentDTO studentDTO) {

        House house = houseService.findHouseByName(studentDTO.getHouseName());


        if (house != null) {
            studentDTO.setHouse(house);
        } else {

            house = new House();
            house.setName(studentDTO.getHouseName());


            studentDTO.setHouse(house);
        }

        studentRepository.save(studentDTO);
    }


    public StudentDTO updateStudent(int id, StudentDTO student) {
        Long longId = Long.valueOf(id); // Convert int to Long
        Optional<StudentDTO> existingStudentOptional = studentRepository.findById(longId);
        if (existingStudentOptional.isPresent()) {
            StudentDTO existingStudent = existingStudentOptional.get();
            existingStudent.setFirstName(student.getFirstName());
            existingStudent.setMiddleName(student.getMiddleName());
            existingStudent.setLastName(student.getLastName());
            existingStudent.setDateOfBirth(student.getDateOfBirth());
            existingStudent.setHouse(student.getHouse());
            existingStudent.setPrefect(student.isPrefect());
            existingStudent.setEnrollmentYear(student.getEnrollmentYear());
            existingStudent.setGraduationYear(student.isGraduationYear());


            return studentRepository.save(existingStudent);
        } else {

            return null;
        }
    }

    public void deleteStudent(int id) {
        Long longId = Long.valueOf(id); // Convert int to Long
        if (studentRepository.existsById(longId)) {
            studentRepository.deleteById(longId);
        } else {
            throw new RuntimeException("Student with ID " + id + " not found");
        }
    }

    public Optional<StudentDTO> createStudentFromJSON(String jsonData) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {

            StudentDTO student = objectMapper.readValue(jsonData, StudentDTO.class);
            return Optional.ofNullable(student);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    // Corrected method signature
    public List<StudentDTO> saveAllStudents(List<StudentDTO> students) {
        return studentRepository.saveAll(students);
    }

    public List<StudentDTO> getAllStudents() {
        return studentRepository.findAll();
    }

    public Optional<StudentDTO> getStudentDTOById(int id) {
        Long longId = Long.valueOf(id); // Convert int to Long
        Optional<StudentDTO> studentOptional = studentRepository.findById(longId);
        return studentOptional.map(this::mapToDTO);
    }

    private StudentDTO mapToDTO(StudentDTO student) {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setFirstName(student.getFirstName());
        studentDTO.setMiddleName(student.getMiddleName());
        studentDTO.setLastName(student.getLastName());

        return studentDTO;
    }


    public StudentDTO createStudent(StudentDTO studentDTO) {
        // Save the studentDTO to the database
        return studentRepository.save(studentDTO);
    }
}
