package com.example.sgswimming.services.instructor;

import com.example.sgswimming.model.ClientData;
import com.example.sgswimming.model.Instructor;
import com.example.sgswimming.model.Lesson;
import com.example.sgswimming.repositories.ClientDataRepository;
import com.example.sgswimming.repositories.InstructorRepository;
import com.example.sgswimming.repositories.LessonRepository;
import com.example.sgswimming.repositories.SwimmerRepository;
import com.example.sgswimming.services.InstructorService;
import com.example.sgswimming.services.InstructorServiceImpl;
import com.example.sgswimming.web.DTOs.read.InstructorReadDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class InstructorServiceMultiTenancyIT {

    @Autowired
    InstructorRepository repository;

    @Autowired
    LessonRepository lessonRepository;

    @Autowired
    ClientDataRepository clientDataRepository;

    @Autowired
    SwimmerRepository swimmerRepository;

    InstructorService service;

    @BeforeEach
    void setUp() {
        service = new InstructorServiceImpl(
                clientDataRepository,
                repository,
                lessonRepository,
                swimmerRepository);
    }

    static final Long ID = 1L;
    static final String FIRST_NAME = "first name";
    static final String LAST_NAME = "last name";

    @Test
    void findAllWithClientData() {
        //given
        List<Lesson> lessons = List.of(new Lesson());
        List<Lesson> savedLessons = lessonRepository.saveAll(lessons);

        Instructor instructor = new Instructor();
        instructor.setFirstName(FIRST_NAME);
        instructor.setLastName(LAST_NAME);
        Instructor savedInstructor = repository.save(instructor);

        ClientData clientData = new ClientData();
        ClientData savedClientData = clientDataRepository.save(clientData);

        savedInstructor.setLessons(savedLessons);
        savedLessons.forEach(lesson -> lesson.setInstructor(instructor));
        savedInstructor = repository.save(savedInstructor);

        savedClientData.setInstructor(savedInstructor);
        savedInstructor.addClientData(savedClientData);

        savedClientData = clientDataRepository.save(savedClientData);

        //do
        List<InstructorReadDto> instructorReadDtoList = service.findAll(savedClientData);

        //test
        assertEquals(1, instructorReadDtoList.size());
    }

    @Test
    void findByIdWithClientData() {
        //given
        List<Lesson> lessons = List.of(new Lesson());
        List<Lesson> savedLessons = lessonRepository.saveAll(lessons);

        Instructor instructor = new Instructor();
        instructor.setFirstName(FIRST_NAME);
        instructor.setLastName(LAST_NAME);
        Instructor savedInstructor = repository.save(instructor);

        ClientData clientData = new ClientData();
        ClientData savedClientData = clientDataRepository.save(clientData);

        savedInstructor.setLessons(savedLessons);
        savedLessons.forEach(lesson -> lesson.setInstructor(instructor));
        savedInstructor = repository.save(savedInstructor);

        savedClientData.setInstructor(savedInstructor);
        savedInstructor.addClientData(savedClientData);

        savedClientData = clientDataRepository.save(savedClientData);

        //do
        InstructorReadDto instructorReadDto = service.findById(savedClientData, savedInstructor.getId());

        //test
        assertNotNull(instructorReadDto);
    }
}
