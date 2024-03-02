package edu.hogwarts.repositories;

import edu.hogwarts.models.dto.StudentDTO;
import org.springframework.data.jpa.repository.JpaRepository;


public interface StudentRepository extends JpaRepository<StudentDTO, Long> {

}
