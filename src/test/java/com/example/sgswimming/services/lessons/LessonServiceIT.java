package com.example.sgswimming.services.lessons;

import com.example.sgswimming.model.Instructor;
import com.example.sgswimming.model.Swimmer;
import com.example.sgswimming.model.Lesson;
import com.example.sgswimming.model.exceptions.NotFoundException;
import com.example.sgswimming.repositories.*;
import com.example.sgswimming.repositories.SwimmerRepository;
import com.example.sgswimming.services.LessonService;
import com.example.sgswimming.services.LessonServiceImpl;
import com.example.sgswimming.web.DTOs.read.LessonReadDto;
import com.example.sgswimming.web.DTOs.save.LessonSaveDto;
import com.example.sgswimming.web.DTOs.update.LessonUpdateDto;
import com.example.sgswimming.web.config.JsonDateMappingConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class LessonServiceIT {

    @Autowired
    LessonRepository repository;

    @Autowired
    SwimmerRepository swimmerRepository;

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

    static final String DESCRIPTION = "description";
    LocalDateTime LOCAL_DATE_TIME = LocalDateTime.now();
    String LOCAL_DATE_TIME_STRING = LOCAL_DATE_TIME.format(DateTimeFormatter.ofPattern(JsonDateMappingConfig.DATE_TIME_FORMAT));

    @Test
    void findAll() {
        //do
        List<LessonReadDto> foundLessons = service.findAll();

        //test
        assertFalse(foundLessons.isEmpty());
    }

    @Test
    void findById() {
        //given
        Swimmer swimmer = new Swimmer();
        swimmer.setFirstName(FIRST_NAME);
        swimmer.setLastName(LAST_NAME);
        Swimmer savedSwimmer = swimmerRepository.save(swimmer);

        Instructor instructor = new Instructor();
        instructor.setFirstName(FIRST_NAME);
        instructor.setLastName(LAST_NAME);
        Instructor savedInstructor = instructorRepository.save(instructor);

        Lesson lesson = new Lesson();
        Lesson savedLesson = repository.save(lesson);

        savedInstructor.setLessons(List.of(savedLesson));
        savedSwimmer.setLessons(List.of(savedLesson));
        savedLesson.setInstructor(savedInstructor);
        savedLesson.setSwimmers(List.of(savedSwimmer));
        repository.save(savedLesson);

        //do
        LessonReadDto foundLessonDto = service.findById(savedLesson.getId());

        //test
        assertNotNull(foundLessonDto);
        assertNotNull(foundLessonDto.getInstructor());
        assertFalse(foundLessonDto.getSwimmers().isEmpty());
    }

    @Test
    void save() {
        LessonSaveDto dto = new LessonSaveDto();
        dto.setDescription(DESCRIPTION);
        dto.setLocalDateTime(LOCAL_DATE_TIME_STRING);

        Long savedEntityId = service.save(dto).getId();

        Lesson lesson = repository.findById(savedEntityId).get();

        assertNotNull(lesson);
        assertEquals(DESCRIPTION, lesson.getDescription());
        assertNotNull(lesson.getLocalDateTime());

        assertTrue(lesson.getSwimmers().isEmpty());
        assertNull(lesson.getInstructor());
    }

    @Test
    void update() {
        //given
        Lesson lesson = new Lesson();
        Long savedLessonId = repository.save(lesson).getId();

        LessonUpdateDto dto = new LessonUpdateDto();
        dto.setId(savedLessonId);
        dto.setDescription(DESCRIPTION);
        dto.setLocalDateTime(LOCAL_DATE_TIME_STRING);

        Long savedEntityId = service.update(dto).getId();

        Lesson foundLesson = repository.findById(savedEntityId).get();

        assertNotNull(foundLesson);
        assertEquals(DESCRIPTION, foundLesson.getDescription());
        assertNotNull(foundLesson.getLocalDateTime());

        assertTrue(foundLesson.getSwimmers().isEmpty());
        assertNull(foundLesson.getInstructor());
    }

    @Test
    void updateIllegalStateException() {
        //given
        LessonUpdateDto dto = new LessonUpdateDto();
        dto.setId(null);
        dto.setDescription(DESCRIPTION);
        dto.setLocalDateTime(LOCAL_DATE_TIME_STRING);

        //do
        assertThrows(IllegalArgumentException.class, () -> {
            service.update(dto);
        });
    }

    @Test
    void updateSwimmerNotFoundException() {
        //given
        LessonUpdateDto dto = new LessonUpdateDto();
        dto.setId(412412421L);
        dto.setDescription(DESCRIPTION);
        dto.setLocalDateTime(LOCAL_DATE_TIME_STRING);

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
        Swimmer savedSwimmer = swimmerRepository.save(swimmer);

        Instructor instructor = new Instructor();
        instructor.setFirstName(FIRST_NAME);
        instructor.setLastName(LAST_NAME);
        Instructor savedInstructor = instructorRepository.save(instructor);

        Lesson lesson = new Lesson();
        Lesson savedLesson = repository.save(lesson);

        savedInstructor.setLessons(List.of(savedLesson));
        savedSwimmer.setLessons(List.of(savedLesson));
        savedLesson.setInstructor(savedInstructor);
        savedLesson.setSwimmers(List.of(savedSwimmer));

        Long savedEntityId = repository.save(savedLesson).getId();

        service.deleteById(savedEntityId);

        Optional<Lesson> lessonOptional = repository.findById(savedEntityId);

        assertFalse(lessonOptional.isPresent());
        assertTrue(swimmerRepository.findAllByLessonsId(savedEntityId).isEmpty());
        assertTrue(instructorRepository.findAllByLessonsId(savedEntityId).isEmpty());
    }
}
