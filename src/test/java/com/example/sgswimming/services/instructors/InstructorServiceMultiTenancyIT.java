package com.example.sgswimming.services.instructors;

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
import org.springframework.test.annotation.DirtiesContext;

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
                repository,
                lessonRepository);
    }

    static final Long ID = 1L;
    static final String FIRST_NAME = "first name";
    static final String LAST_NAME = "last name";

    private Instructor getSavedInstructor() {
        Instructor instructor = new Instructor();
        instructor.setFirstName(FIRST_NAME);
        instructor.setLastName(LAST_NAME);
        return repository.save(instructor);
    }

    private ClientData getSavedClientData() {
        ClientData clientData = new ClientData();
        return clientDataRepository.save(clientData);
    }

    @Test
    void findAllWithClientData() {
        //given
        List<Lesson> lessons = List.of(new Lesson());
        List<Lesson> savedLessons = lessonRepository.saveAll(lessons);

        Instructor savedInstructor = getSavedInstructor();
        ClientData savedClientData = getSavedClientData();
        savedInstructor.setLessons(savedLessons);
        for (Lesson lesson : savedLessons) lesson.setInstructor(savedInstructor);
        savedInstructor = repository.save(savedInstructor);

        savedClientData.setInstructor(savedInstructor);
        savedInstructor.addClientData(savedClientData);
        savedClientData = clientDataRepository.save(savedClientData);

        Instructor anotherSavedInstructor = getSavedInstructor();
        ClientData anotherSavedClientData = getSavedClientData();
        anotherSavedClientData.setInstructor(anotherSavedInstructor);
        anotherSavedInstructor.addClientData(anotherSavedClientData);
        clientDataRepository.save(anotherSavedClientData);

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

        Instructor savedInstructor = getSavedInstructor();
        ClientData savedClientData = getSavedClientData();
        savedInstructor.setLessons(savedLessons);
        for (Lesson lesson : savedLessons) lesson.setInstructor(savedInstructor);
        savedInstructor = repository.save(savedInstructor);

        savedClientData.setInstructor(savedInstructor);
        savedInstructor.addClientData(savedClientData);
        savedClientData = clientDataRepository.save(savedClientData);

        Instructor anotherSavedInstructor = getSavedInstructor();
        ClientData anotherSavedClientData = getSavedClientData();
        anotherSavedClientData.setInstructor(anotherSavedInstructor);
        anotherSavedInstructor.addClientData(anotherSavedClientData);
        clientDataRepository.save(anotherSavedClientData);

        //do
        InstructorReadDto instructorReadDto = service.findById(savedClientData, savedInstructor.getId());

        //test
        assertNotNull(instructorReadDto);
    }
}