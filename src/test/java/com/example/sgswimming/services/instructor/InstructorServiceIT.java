package com.example.sgswimming.services.instructor;

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

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
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
        service = new InstructorServiceImpl(repository, lessonRepository);
    }

    static final Long ID = 1L;
    static final String FIRST_NAME = "first name";
    static final String LAST_NAME = "last name";

    @Test
    void findAll() {

        //given
        Instructor instructor = new Instructor();
        instructor.setFirstName(FIRST_NAME);
        instructor.setLastName(LAST_NAME);

        List<Lesson> lessons = List.of(new Lesson());
        List<Lesson> savedLessons = lessonRepository.saveAll(lessons);

        Instructor savedInstructor = repository.save(instructor);
        savedInstructor.setLessons(savedLessons);
        savedLessons.forEach(lesson -> lesson.setInstructor(instructor));

        Long savedEntityId = repository.save(savedInstructor).getId();

        //do
        List<InstructorReadDto> instructorReadDtoList = service.findAll();

        //test
        assertEquals(1, instructorReadDtoList.size());
        instructorReadDtoList.forEach(Assertions::assertNotNull);
        instructorReadDtoList.forEach(dto -> assertFalse(dto.getLessons().isEmpty()));
        instructorReadDtoList.forEach(dto -> assertTrue(dto
                .getLessons()
                .stream()
                .allMatch(lessonReadDto -> lessonReadDto.getInstructor().getId().equals(savedEntityId))));
    }

    @Test
    void findById() {
        //given
        Instructor instructor = new Instructor();
        instructor.setFirstName(FIRST_NAME);
        instructor.setLastName(LAST_NAME);

        List<Lesson> lessons = List.of(new Lesson());
        List<Lesson> savedLessons = lessonRepository.saveAll(lessons);

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
    void updateIllegalStateException() {
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
        List<Lesson> savedLessons = lessonRepository.saveAll(lessons);

        Instructor savedInstructor = repository.save(instructor);
        savedInstructor.setLessons(savedLessons);
        savedLessons.forEach(lesson -> lesson.setInstructor(instructor));

        Long savedEntityId = repository.save(savedInstructor).getId();

        service.deleteById(savedEntityId);

        Optional<Instructor> instructorOptional = repository.findById(savedEntityId);

        assertFalse(instructorOptional.isPresent());
        assertTrue(lessonRepository.findAllDistinctByInstructorId(savedEntityId).isEmpty());
    }
}
