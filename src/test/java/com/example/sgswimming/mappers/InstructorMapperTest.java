package com.example.sgswimming.mappers;

import com.example.sgswimming.DTOs.InstructorDTO;
import com.example.sgswimming.model.Instructor;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class InstructorMapperTest {

    String FIRST_NAME = "John";
    String LAST_NAME = "Kowalski";

    InstructorMapper mapper = InstructorMapper.INSTANCE;
    InstructorMapper.Skinny skinnyMapper = InstructorMapper.Skinny.INSTANCE;

    @Test
    void objectToDTO() {
        Instructor instructor =
                Instructor.builder()
                        .firstName(FIRST_NAME)
                        .lastName(LAST_NAME)
                        .build();

        InstructorDTO dto = mapper.toDto(instructor);

        assertEquals(instructor.getFirstName(), dto.getFirstName());
        assertEquals(instructor.getLastName(), dto.getLastName());
        assertNotNull(instructor.getLessons());
    }

    @Test
    void objectToSkinnyDTO() {
        Instructor instructor =
                Instructor.builder()
                        .firstName(FIRST_NAME)
                        .lastName(LAST_NAME)
                        .build();

        InstructorDTO.Skinny dto = skinnyMapper.toDto(instructor);

        assertEquals(instructor.getFirstName(), dto.getFirstName());
        assertEquals(instructor.getLastName(), dto.getLastName());
        assertNotNull(dto.getLessonIds());
    }

    @Test
    void DTOtoObject() {
        InstructorDTO dto = InstructorDTO
                .builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .build();

        Instructor instructor = mapper.toInstructor(dto);

        assertEquals(dto.getFirstName(), instructor.getFirstName());
        assertEquals(dto.getLastName(), instructor.getLastName());
        assertNotNull(instructor.getLessons());
    }
}
