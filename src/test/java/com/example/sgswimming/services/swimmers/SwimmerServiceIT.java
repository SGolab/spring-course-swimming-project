package com.example.sgswimming.services.swimmers;

import com.example.sgswimming.model.Swimmer;
import com.example.sgswimming.model.Lesson;
import com.example.sgswimming.model.exceptions.NotFoundException;
import com.example.sgswimming.repositories.*;
import com.example.sgswimming.repositories.SwimmerRepository;
import com.example.sgswimming.services.SwimmerService;
import com.example.sgswimming.services.SwimmerServiceImpl;
import com.example.sgswimming.web.DTOs.read.SwimmerReadDto;
import com.example.sgswimming.web.DTOs.save.SwimmerSaveDto;
import com.example.sgswimming.web.DTOs.update.SwimmerUpdateDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SwimmerServiceIT {

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
        service = new SwimmerServiceImpl(repository, lessonRepository);
    }

    static final Long ID = 1L;
    static final String FIRST_NAME = "first name";
    static final String LAST_NAME = "last name";

    @Test
    void findAll() {
        //do
        List<SwimmerReadDto> swimmerReadDtoList = service.findAll();

        //test
        assertFalse(swimmerReadDtoList.isEmpty());
    }

    @Test
    void findById() {
        //given
        Swimmer swimmer = new Swimmer();
        swimmer.setFirstName(FIRST_NAME);
        swimmer.setLastName(LAST_NAME);

        List<Lesson> lessons = List.of(new Lesson());
        Set<Lesson> savedLessons = new HashSet<>(lessonRepository.saveAll(lessons));

        Swimmer savedSwimmer = repository.save(swimmer);
        savedSwimmer.setLessons(savedLessons);
        savedLessons.forEach(lesson -> lesson.addSwimmer(savedSwimmer));

        Long savedEntityId = repository.save(savedSwimmer).getId();

        //do
        SwimmerReadDto swimmerReadDto = service.findById(savedEntityId);

        //test
        assertNotNull(swimmerReadDto);
        assertFalse(swimmerReadDto.getLessons().isEmpty());
        assertTrue(swimmerReadDto
                .getLessons()
                .stream()
                .allMatch(lessonReadDto -> lessonReadDto.getSwimmers().stream()
                        .allMatch(s -> s.getId().equals(savedEntityId))));
    }

    @Test
    void save() {
        SwimmerSaveDto dto = new SwimmerSaveDto();
        dto.setFirstName(FIRST_NAME);
        dto.setLastName(LAST_NAME);

        Long savedEntityId = service.save(dto).getId();

        Swimmer swimmer = repository.findById(savedEntityId).get();

        assertNotNull(swimmer);
        assertEquals(FIRST_NAME, swimmer.getFirstName());
        assertEquals(LAST_NAME, swimmer.getLastName());
        assertTrue(swimmer.getLessons().isEmpty());
    }

    @Test
    void update() {
        //given
        List<Lesson> lessons = List.of(new Lesson());
        List<Lesson> savedLessons = lessonRepository.saveAll(lessons);

        Set<Long> ids = savedLessons
                .stream()
                .map(Lesson::getId)
                .collect(Collectors.toSet());

        Swimmer swimmer = new Swimmer();
        swimmer.setFirstName("AA");
        swimmer.setLastName("BB");
        Long savedEntityId = repository.save(swimmer).getId();

        SwimmerUpdateDto dto = new SwimmerUpdateDto();
        dto.setId(savedEntityId);
        dto.setFirstName(FIRST_NAME);
        dto.setLastName(LAST_NAME);
        dto.setLessons(ids);

        //do
        SwimmerReadDto swimmerFatDto = service.update(dto);

        //test
        assertNotNull(swimmerFatDto);
        assertEquals(FIRST_NAME, swimmerFatDto.getFirstName());
        assertEquals(LAST_NAME, swimmerFatDto.getLastName());
        assertFalse(swimmerFatDto.getLessons().isEmpty());

        ids.forEach(id -> assertTrue(swimmerFatDto.getLessons().stream().anyMatch(lesson -> lesson.getId().equals(id))));
        ids.forEach(id -> assertFalse(lessonRepository.findById(id).get().getSwimmers().isEmpty()));
    }

    @Test
    void updateIllegalStateException() {
        //given
        SwimmerUpdateDto dto = new SwimmerUpdateDto();
        dto.setId(null);
        dto.setFirstName(FIRST_NAME);
        dto.setLastName(LAST_NAME);
        dto.setLessons(Set.of(1L));

        //do
        assertThrows(IllegalArgumentException.class, () -> {
            service.update(dto);
        });
    }

    @Test
    void updateSwimmerNotFoundException() {
        //given
        SwimmerUpdateDto dto = new SwimmerUpdateDto();
        dto.setId(3124125354L);
        dto.setFirstName(FIRST_NAME);
        dto.setLastName(LAST_NAME);
        dto.setLessons(Set.of(1L));

        //do
        assertThrows(NotFoundException.class, () -> {
            service.update(dto);
        });
    }

    @Test
    void deleteById() {
        Swimmer swimmer = new Swimmer();
        swimmer.setFirstName(FIRST_NAME);
        swimmer.setLastName(LAST_NAME);

        List<Lesson> lessons = List.of(new Lesson());
        Set<Lesson> savedLessons = new HashSet<>(lessonRepository.saveAll(lessons));

        Swimmer savedSwimmer = repository.save(swimmer);
        savedSwimmer.setLessons(savedLessons);
        savedLessons.forEach(lesson -> lesson.addSwimmer(swimmer));

        Long savedEntityId = repository.save(savedSwimmer).getId();

        service.deleteById(savedEntityId);

        Optional<Swimmer> swimmerOptional = repository.findById(savedEntityId);

        assertFalse(swimmerOptional.isPresent());
        assertTrue(lessonRepository.findAllBySwimmersId(savedEntityId).isEmpty());
    }
}
