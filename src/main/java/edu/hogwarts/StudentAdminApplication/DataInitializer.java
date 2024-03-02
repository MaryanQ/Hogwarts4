package edu.hogwarts.StudentAdminApplication;

import edu.hogwarts.models.Course;
import edu.hogwarts.models.House;
import edu.hogwarts.models.Teacher;

import edu.hogwarts.services.HouseService;
import edu.hogwarts.models.dto.StudentDTO;
import edu.hogwarts.repositories.CourseRepository;
import edu.hogwarts.repositories.HouseRepository;
import edu.hogwarts.repositories.StudentRepository;
import edu.hogwarts.repositories.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private HouseService houseService;

    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    private final HouseRepository houseRepository;

    @Autowired
    public DataInitializer(TeacherRepository teacherRepository,
                           StudentRepository studentRepository, CourseRepository courseRepository, HouseRepository houseRepository) {

        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.houseRepository = houseRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        teachers();
        house();
        students();
        courses();

    }


    private void teachers() {
        List<Teacher> teachers = new ArrayList<>();
        Teacher teacher1 = new Teacher(1, "Minerva", "", "McGonagall", new Date(), "Gryffindor", true, Teacher.Emptype.TENURED, LocalDate.now(), LocalDate.now());
        Teacher teacher2 = new Teacher(2, "Severus", "", "Snape", new Date(), "Slytherin", false, Teacher.Emptype.TEMPORARY, LocalDate.now(), LocalDate.now());
        Teacher teacher3 = new Teacher(3, "Filius", "", "Flitwick", new Date(), "Ravenclaw", false, Teacher.Emptype.TEMPORARY, LocalDate.now(), LocalDate.now());
        Teacher teacher4 = new Teacher(4, "Pomona", "", "Sprout", new Date(), "Hufflepuff", false, Teacher.Emptype.TEMPORARY, LocalDate.now(), LocalDate.now());
        Teacher teacher5 = new Teacher(5, "Remus", "", "Lupin", new Date(), "Gryffindor", false, Teacher.Emptype.TEMPORARY, LocalDate.now(), LocalDate.now());
        Teacher teacher6 = new Teacher(6, "Alastor", "Mad-Eye", "Moody", new Date(), "N/A", false, Teacher.Emptype.TEMPORARY, LocalDate.now(), LocalDate.now());

        teachers.add(teacher1);
        teachers.add(teacher2);
        teachers.add(teacher3);
        teachers.add(teacher4);
        teachers.add(teacher5);
        teachers.add(teacher6);

        teacherRepository.saveAll(teachers);
    }

    private void students() {
        House gryffindorHouse = houseService.findHouseByName("Gryffindor");
        House slytherinHouse = houseService.findHouseByName("Slytherin");
        House ravenclawHouse = houseService.findHouseByName("Ravenclaw");

        List<StudentDTO> students = new ArrayList<>();

        students.add(new StudentDTO(1, "Harry", "James", "Potter", LocalDate.of(1980, 7, 31), gryffindorHouse, false, 1991, 1990, 1995));
        students.add(new StudentDTO(2, "Hermione", "Jean", "Granger", LocalDate.of(1979, 9, 19), gryffindorHouse, true, 1991, 1990, 1995));
        students.add(new StudentDTO(3, "Ron", "Bilius", "Weasley", LocalDate.of(1980, 3, 1), gryffindorHouse, false, 1991, 1990, 1995));
        students.add(new StudentDTO(4, "Draco", "", "Malfoy", LocalDate.of(1980, 6, 5), slytherinHouse, false, 1991, 1990, 1995));
        students.add(new StudentDTO(5, "Luna", "", "Lovegood", LocalDate.of(1981, 2, 13), ravenclawHouse, false, 1992, 1990, 1995));
        students.add(new StudentDTO(6, "Neville", "Longbottom", "", LocalDate.of(1980, 7, 30), gryffindorHouse, false, 1991, 1990, 1995));
        students.add(new StudentDTO(7, "Ginny", "", "Weasley", LocalDate.of(1981, 8, 11), gryffindorHouse, false, 1992, 1990, 1995));
        students.add(new StudentDTO(8, "Fred", "", "Weasley", LocalDate.of(1978, 4, 1), gryffindorHouse, false, 1989, 1990, 1995));
        students.add(new StudentDTO(9, "George", "", "Weasley", LocalDate.of(1978, 4, 1), gryffindorHouse, false, 1989, 1990, 1995));
        students.add(new StudentDTO(10, "Percy", "", "Weasley", LocalDate.of(1976, 8, 22), gryffindorHouse, false, 1987, 1990, 1995));
        students.add(new StudentDTO(11, "Oliver", "", "Wood", LocalDate.of(1975, 1, 7), gryffindorHouse, false, 1986, 1990, 1995));
        students.add(new StudentDTO(12, "Cho", "", "Chang", LocalDate.of(1979, 7, 1), ravenclawHouse, false, 1990, 1990,1995));

        studentRepository.saveAll(students);
    }


    private void courses() {
        List<Course> courses = new ArrayList<>();

        Course course1 = new Course("Potions", 1, true);
        Course course2 = new Course("Transfiguration", 1, true);
        Course course3 = new Course("Charms", 1, true);

        // Set IDs for courses
        course1.setId(1);
        course2.setId(2);
        course3.setId(3);

        courses.add(course1);
        courses.add(course2);
        courses.add(course3);

        courseRepository.saveAll(courses);
    }

    private void house() {
        List<House> houseDTOs = new ArrayList<>();
        houseDTOs.add(new House("Gryffindor", "Godric Gryffindor", "Scarlet and gold"));
        houseDTOs.add(new House("Hufflepuff", "Helga Hufflepuff", "Yellow and black"));
        houseDTOs.add(new House("Ravenclaw", "Rowena Ravenclaw", "Blue and bronze"));
        houseDTOs.add(new House("Slytherin", "Salazar Slytherin", "Emerald green and silver"));

        List<House> houses = new ArrayList<>();
        for (House houseDTO : houseDTOs) {
            House house = new House();
            house.setName(houseDTO.getName());
            house.setFounder(houseDTO.getFounder());
            house.setColors(houseDTO.getColors());
            houses.add(house);
        }

        houseRepository.saveAll(houses);
    }
}

