package com.example.sgswimming.mappers;

import com.example.sgswimming.model.Instructor;
import com.example.sgswimming.web.DTOs.InstructorFatDto;
import com.example.sgswimming.web.DTOs.InstructorSkinnyDto;
import com.example.sgswimming.web.mappers.InstructorMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class InstructorMapperTest {

    String FIRST_NAME = "John";
    String LAST_NAME = "Kowalski";

    InstructorMapper mapper = InstructorMapper.getInstance();

    @Test
    void objectToDTO() {
        Instructor instructor =
                Instructor.builder()
                        .firstName(FIRST_NAME)
                        .lastName(LAST_NAME)
                        .build();

        InstructorFatDto dto = mapper.toFatDto(instructor);

        assertEquals(FIRST_NAME, dto.getFirstName());
        assertEquals(LAST_NAME, dto.getLastName());
        assertNotNull(instructor.getLessons());
    }

    @Test
    void DTOtoObject() {
        InstructorFatDto dto = InstructorFatDto
                .builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .build();

        Instructor instructor = mapper.fromFatToInstructor(dto);

        assertEquals(FIRST_NAME, instructor.getFirstName());
        assertEquals(LAST_NAME, instructor.getLastName());
        assertNotNull(instructor.getLessons());
    }

    @Test
    void objectToSkinnyDTO() {
        Instructor instructor =
                Instructor.builder()
                        .firstName(FIRST_NAME)
                        .lastName(LAST_NAME)
                        .build();

        InstructorSkinnyDto dto = mapper.toSkinnyDto(instructor);

        assertEquals(FIRST_NAME, dto.getFirstName());
        assertEquals(LAST_NAME, dto.getLastName());
        assertNotNull(dto.getLessonIds());
    }

    @Test
    void skinnyDtoToObject() {
        InstructorSkinnyDto dto =
                InstructorSkinnyDto.builder()
                        .firstName(FIRST_NAME)
                        .lastName(LAST_NAME)
                        .build();

        Instructor instructor = mapper.fromSkinnyToInstructor(dto);

        assertEquals(FIRST_NAME, instructor.getFirstName());
        assertEquals(LAST_NAME, instructor.getLastName());
        assertNotNull(instructor.getLessons());
    }
}
