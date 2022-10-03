package com.example.sgswimming.services.instructors;

import com.example.sgswimming.model.Instructor;
import com.example.sgswimming.model.Lesson;
import com.example.sgswimming.model.exceptions.NotFoundException;
import com.example.sgswimming.repositories.ClientDataRepository;
import com.example.sgswimming.repositories.InstructorRepository;
import com.example.sgswimming.repositories.LessonRepository;
import com.example.sgswimming.repositories.SwimmerRepository;
import com.example.sgswimming.services.InstructorService;
import com.example.sgswimming.services.InstructorServiceImpl;
import com.example.sgswimming.web.DTOs.read.InstructorReadDto;
import com.example.sgswimming.web.DTOs.save.InstructorSaveDto;
import com.example.sgswimming.web.DTOs.update.InstructorUpdateDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class InstructorServiceIT {

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
        service = new InstructorServiceImpl(repository, lessonRepository, clientDataRepository);
    }

    static final Long ID = 1L;
    static final String FIRST_NAME = "first name";
    static final String LAST_NAME = "last name";

    @Test
    void findAll() {
        //do
        List<InstructorReadDto> instructorReadDtoList = service.findAll();

        //test
        assertFalse(instructorReadDtoList.isEmpty());
    }

    @Test
    void findById() {
        //given
        Instructor instructor = new Instructor();
        instructor.setFirstName(FIRST_NAME);
        instructor.setLastName(LAST_NAME);

        List<Lesson> lessons = List.of(new Lesson());
        Set<Lesson> savedLessons = new HashSet<>(lessonRepository.saveAll(lessons));

        Instructor savedInstructor = repository.save(instructor);
        savedInstructor.setLessons(savedLessons);
        savedLessons.forEach(lesson -> lesson.setInstructor(instructor));

        Long savedEntityId = repository.save(savedInstructor).getId();

        //do
        InstructorReadDto instructorReadDto = service.findById(savedEntityId);

        //test
        assertNotNull(instructorReadDto);
        assertFalse(instructorReadDto.getLessons().isEmpty());
        assertTrue(instructorReadDto
                .getLessons()
                .stream()
                .allMatch(lessonReadDto -> lessonReadDto.getInstructor().getId().equals(savedEntityId)));
    }

    @Test
    void save() {
        InstructorSaveDto dto = new InstructorSaveDto();
        dto.setFirstName(FIRST_NAME);
        dto.setLastName(LAST_NAME);

        Long savedEntityId = service.save(dto).getId();

        Instructor instructor = repository.findById(savedEntityId).get();

        assertNotNull(instructor);
        assertEquals(FIRST_NAME, instructor.getFirstName());
        assertEquals(LAST_NAME, instructor.getLastName());
        assertTrue(instructor.getLessons().isEmpty());
    }

    @Test
    void update() {
        //given
        List<Lesson> lessons = List.of(Lesson.builder().instructor(null).build());
        List<Lesson> savedLessons = lessonRepository.saveAll(lessons);

        Set<Long> ids = savedLessons
                .stream()
                .map(Lesson::getId)
                .collect(Collectors.toSet());

        Instructor instructor = new Instructor();
        instructor.setFirstName("AA");
        instructor.setLastName("BB");
        Long savedEntityId = repository.save(instructor).getId();

        InstructorUpdateDto dto = new InstructorUpdateDto();
        dto.setId(savedEntityId);
        dto.setFirstName(FIRST_NAME);
        dto.setLastName(LAST_NAME);
        dto.setLessons(ids);

        //do
        InstructorReadDto instructorFatDto = service.update(dto);

        //test
        assertNotNull(instructorFatDto);
        assertEquals(FIRST_NAME, instructorFatDto.getFirstName());
        assertEquals(LAST_NAME, instructorFatDto.getLastName());
        assertFalse(instructorFatDto.getLessons().isEmpty());

        ids.forEach(id -> assertTrue(instructorFatDto.getLessons().stream().anyMatch(lesson -> lesson.getId().equals(id))));
        ids.forEach(id -> assertNotNull(lessonRepository.findById(id).get().getInstructor()));
    }

    @Test
    void updateIdNull() {
        //given
        InstructorUpdateDto dto = new InstructorUpdateDto();
        dto.setId(null);
        dto.setFirstName(FIRST_NAME);
        dto.setLastName(LAST_NAME);
        dto.setLessons(Set.of(1L));

        //do
        assertThrows(IllegalArgumentException.class, () ->{
            service.update(dto);
        });
    }

    @Test
    void updateInstructorNotFoundException() {
        //given
        InstructorUpdateDto dto = new InstructorUpdateDto();
        dto.setId(3124125354L);
        dto.setFirstName(FIRST_NAME);
        dto.setLastName(LAST_NAME);
        dto.setLessons(Set.of(1L));

        //do
        assertThrows(NotFoundException.class, () ->{
            service.update(dto);
        });
    }

    @Test
    void deleteById() {
        Instructor instructor = new Instructor();
        instructor.setFirstName(FIRST_NAME);
        instructor.setLastName(LAST_NAME);

        List<Lesson> lessons = List.of(new Lesson());
        Set<Lesson> savedLessons = new HashSet<>(lessonRepository.saveAll(lessons));

        Instructor savedInstructor = repository.save(instructor);
        savedInstructor.setLessons(savedLessons);
        savedLessons.forEach(lesson -> lesson.setInstructor(instructor));

        Long savedEntityId = repository.save(savedInstructor).getId();

        service.deleteById(savedEntityId);

        Optional<Instructor> instructorOptional = repository.findById(savedEntityId);

        assertFalse(instructorOptional.isPresent());

        assertTrue(lessonRepository
                .findAll()
                .stream()
                .map(Lesson::getInstructor)
                .filter(Objects::nonNull)
                .noneMatch(instr -> instr.getId().equals(savedInstructor.getId())));
    }
}
