package com.example.sgswimming.services.lessons;

import com.example.sgswimming.model.ClientData;
import com.example.sgswimming.model.Instructor;
import com.example.sgswimming.model.Lesson;
import com.example.sgswimming.model.Swimmer;
import com.example.sgswimming.repositories.ClientDataRepository;
import com.example.sgswimming.repositories.InstructorRepository;
import com.example.sgswimming.repositories.LessonRepository;
import com.example.sgswimming.repositories.SwimmerRepository;
import com.example.sgswimming.services.LessonService;
import com.example.sgswimming.services.LessonServiceImpl;
import com.example.sgswimming.services.SwimmerService;
import com.example.sgswimming.services.SwimmerServiceImpl;
import com.example.sgswimming.web.DTOs.read.LessonReadDto;
import com.example.sgswimming.web.DTOs.read.SwimmerReadDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class LessonServiceMultiTenancyIT {

    @Autowired
    SwimmerRepository swimmerRepository;

    @Autowired
    LessonRepository repository;

    @Autowired
    ClientDataRepository clientDataRepository;

    @Autowired
    InstructorRepository instructorRepository;

    LessonService service;

    @BeforeEach
    void setUp() {
        service = new LessonServiceImpl(repository, instructorRepository, swimmerRepository);
    }

    static final Long ID = 1L;
    static final String FIRST_NAME = "first name";
    static final String LAST_NAME = "last name";

    private Lesson getSavedLesson() {
        Lesson lesson = new Lesson();
        return repository.save(lesson);
    }

    private Swimmer getSavedSwimmer() {
        Swimmer swimmer = new Swimmer();
        return swimmerRepository.save(swimmer);
    }

    private Instructor getSavedInstructor() {
        Instructor instructor = new Instructor();
        return instructorRepository.save(instructor);
    }

    private ClientData getSavedClientData() {
        ClientData clientData = new ClientData();
        return clientDataRepository.save(clientData);
    }

    @Test
    void findAllWithClientDataSwimmer() {
        //given
        Lesson savedLesson = getSavedLesson();
        Swimmer savedSwimmer = getSavedSwimmer();
        ClientData savedClientData = getSavedClientData();

        savedSwimmer.addLesson(savedLesson);
        savedLesson.setSwimmers(List.of(savedSwimmer));

        savedClientData.addSwimmer(savedSwimmer);
        savedSwimmer.addClientData(savedClientData);

        savedClientData = clientDataRepository.save(savedClientData);

        //do
        List<LessonReadDto> swimmerReadDtoList = service.findAll(savedClientData);

        //test
        assertEquals(1, swimmerReadDtoList.size());
    }

    @Test
    void findAllWithClientDataInstructor() {
        //given
        Lesson savedLesson = getSavedLesson();
        Swimmer savedSwimmer = getSavedSwimmer();
        ClientData savedClientData = getSavedClientData();

        savedSwimmer.addLesson(savedLesson);
        savedLesson.setSwimmers(List.of(savedSwimmer));

        savedClientData.addSwimmer(savedSwimmer);
        savedSwimmer.addClientData(savedClientData);

        savedClientData = clientDataRepository.save(savedClientData);

        //do
        List<LessonReadDto> swimmerReadDtoList = service.findAll(savedClientData);

        //test
        assertEquals(1, swimmerReadDtoList.size());
    }

    @Test
    void findByIdWithClientDataSwimmer() {
        //given
        Lesson savedLesson = getSavedLesson();
        Instructor savedInstructor = getSavedInstructor();
        ClientData savedClientData = getSavedClientData();

        savedInstructor.addLesson(savedLesson);
        savedLesson.setInstructor(savedInstructor);

        savedClientData.setInstructor(savedInstructor);
        savedInstructor.addClientData(savedClientData);

        savedClientData = clientDataRepository.save(savedClientData);

        //do
        LessonReadDto dto = service.findById(savedClientData, savedLesson.getId());

        assertNotNull(dto);
    }

    @Test
    void findByIdWithClientDataInstructor() {
        //given
        Lesson savedLesson = getSavedLesson();
        Instructor savedInstructor = getSavedInstructor();
        ClientData savedClientData = getSavedClientData();

        savedInstructor.addLesson(savedLesson);
        savedLesson.setInstructor(savedInstructor);

        savedClientData.setInstructor(savedInstructor);
        savedInstructor.addClientData(savedClientData);

        savedClientData = clientDataRepository.save(savedClientData);

        //do
        LessonReadDto dto = service.findById(savedClientData, savedLesson.getId());

        assertNotNull(dto);
    }
}
