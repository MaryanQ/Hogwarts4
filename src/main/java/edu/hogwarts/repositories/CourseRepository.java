package edu.hogwarts.repositories;

import edu.hogwarts.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;



public interface CourseRepository extends JpaRepository<Course, Long> {

}
