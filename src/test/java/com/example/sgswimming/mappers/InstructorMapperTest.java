package com.example.sgswimming.mappers;

import com.example.sgswimming.model.Instructor;
import com.example.sgswimming.model.Lesson;
import com.example.sgswimming.web.DTOs.read.InstructorReadDto;
import com.example.sgswimming.web.DTOs.save.InstructorSaveDto;
import com.example.sgswimming.web.DTOs.update.InstructorUpdateDto;
import com.example.sgswimming.web.mappers.InstructorMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class InstructorMapperTest {

    Long ID = 1L;
    String FIRST_NAME = "John";
    String LAST_NAME = "Kowalski";

    InstructorMapper mapper = InstructorMapper.getInstance();

    Instructor instructor;
    InstructorSaveDto instructorSaveDto;
    InstructorUpdateDto instructorUpdateDto;

    @BeforeEach
    void setUp() {
        instructor = new Instructor();
        instructor.setId(1L);
        instructor.setFirstName(FIRST_NAME);
        instructor.setLastName(LAST_NAME);

        instructor.setLessons(Set.of(
                new Lesson(),
                new Lesson(),
                new Lesson()
        ));

        instructorSaveDto = new InstructorSaveDto();
        instructorSaveDto.setFirstName(FIRST_NAME);
        instructorSaveDto.setLastName(LAST_NAME);

        instructorUpdateDto = new InstructorUpdateDto();
        instructorUpdateDto.setId(1L);
        instructorUpdateDto.setFirstName(FIRST_NAME);
        instructorUpdateDto.setLastName(LAST_NAME);
        instructorUpdateDto.setLessons(Set.of(1L, 2L, 3L));
    }

    @Test
    void toReadDto() {
        InstructorReadDto instructorReadDto = mapper.toReadDto(instructor);

        assertEquals(ID, instructorReadDto.getId());
        assertEquals(FIRST_NAME, instructorReadDto.getFirstName());
        assertEquals(LAST_NAME, instructorReadDto.getLastName());
        assertFalse(instructorReadDto.getLessons().isEmpty());
    }

    @Test
    void fromSaveDtoToInstructor() {
        Instructor instructor = mapper.fromSaveDtoToInstructor(instructorSaveDto);

        assertEquals(FIRST_NAME, instructor.getFirstName());
        assertEquals(LAST_NAME, instructor.getLastName());
        assertTrue(instructor.getLessons().isEmpty());
    }

    @Test
    void fromUpdateDtoToInstructor() {
        Instructor instructor = mapper.fromUpdateDtoToInstructor(instructorUpdateDto);

        assertEquals(ID, instructor.getId());
        assertEquals(FIRST_NAME, instructor.getFirstName());
        assertEquals(LAST_NAME, instructor.getLastName());
        assertTrue(instructor.getLessons().isEmpty());
    }
}
