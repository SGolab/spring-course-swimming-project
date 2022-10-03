package com.example.sgswimming.services.swimmers;

import com.example.sgswimming.model.ClientData;
import com.example.sgswimming.model.Instructor;
import com.example.sgswimming.model.Lesson;
import com.example.sgswimming.model.Swimmer;
import com.example.sgswimming.repositories.ClientDataRepository;
import com.example.sgswimming.repositories.InstructorRepository;
import com.example.sgswimming.repositories.LessonRepository;
import com.example.sgswimming.repositories.SwimmerRepository;
import com.example.sgswimming.services.SwimmerService;
import com.example.sgswimming.services.SwimmerServiceImpl;
import com.example.sgswimming.web.DTOs.read.InstructorReadDto;
import com.example.sgswimming.web.DTOs.read.SwimmerReadDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class SwimmerServiceMultiTenancyIT {

    @Autowired
    SwimmerRepository repository;

    @Autowired
    LessonRepository lessonRepository;

    @Autowired
    ClientDataRepository clientDataRepository;

    @Autowired
    InstructorRepository instructorRepository;

    SwimmerService service;

    @BeforeEach
    void setUp() {
        service = new SwimmerServiceImpl(repository, lessonRepository, clientDataRepository);
    }

    static final Long ID = 1L;
    static final String FIRST_NAME = "first name";
    static final String LAST_NAME = "last name";


    private Swimmer getSavedSwimmer() {
        Swimmer swimmer = new Swimmer();
        swimmer.setFirstName(FIRST_NAME);
        swimmer.setLastName(LAST_NAME);
        return repository.save(swimmer);
    }

    private ClientData getSavedClientData() {
        ClientData clientData = new ClientData();
        return clientDataRepository.save(clientData);
    }

    @Test
    void findAllWithClientData() {
        //given
        List<Lesson> lessons = List.of(new Lesson());
        Set<Lesson> savedLessons = new HashSet<>(lessonRepository.saveAll(lessons));

        Swimmer savedSwimmer = getSavedSwimmer();
        savedSwimmer.setLessons(savedLessons);
        for (Lesson lesson : savedLessons) lesson.addSwimmer(savedSwimmer);
        savedSwimmer = repository.save(savedSwimmer);

        ClientData savedClientData = getSavedClientData();
        savedClientData.addSwimmer(savedSwimmer);
        savedSwimmer.addClientData(savedClientData);
        savedClientData = clientDataRepository.save(savedClientData);

        Swimmer anotherSavedSwimmer = getSavedSwimmer();
        ClientData anotherSavedClientData = getSavedClientData();
        anotherSavedClientData.addSwimmer(anotherSavedSwimmer);
        anotherSavedSwimmer.addClientData(anotherSavedClientData);
        clientDataRepository.save(anotherSavedClientData);

        //do
        List<SwimmerReadDto> swimmerReadDtoList = service.findAll(savedClientData);

        //test
        assertEquals(1, swimmerReadDtoList.size());
    }

    @Test
    void findByIdWithClientData() {
        //given
        List<Lesson> lessons = List.of(new Lesson());
        Set<Lesson> savedLessons = new HashSet<>(lessonRepository.saveAll(lessons));

        Swimmer savedSwimmer = getSavedSwimmer();
        savedSwimmer.setLessons(savedLessons);
        for (Lesson lesson : savedLessons) lesson.addSwimmer(savedSwimmer);
        savedSwimmer = repository.save(savedSwimmer);

        ClientData savedClientData = getSavedClientData();
        savedClientData.addSwimmer(savedSwimmer);
        savedSwimmer.addClientData(savedClientData);
        savedClientData = clientDataRepository.save(savedClientData);

        Swimmer anotherSavedSwimmer = getSavedSwimmer();
        ClientData anotherSavedClientData = getSavedClientData();
        anotherSavedClientData.addSwimmer(anotherSavedSwimmer);
        anotherSavedSwimmer.addClientData(anotherSavedClientData);
        clientDataRepository.save(anotherSavedClientData);

        //do
        SwimmerReadDto swimmerReadDto = service.findById(savedSwimmer.getId(), savedClientData);

        //test
        assertNotNull(swimmerReadDto);
    }
}
