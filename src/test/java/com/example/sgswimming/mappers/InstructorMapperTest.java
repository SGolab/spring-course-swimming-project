package com.example.sgswimming.mappers;

import com.example.sgswimming.DTOs.InstructorDTO;
import com.example.sgswimming.model.Instructor;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InstructorMapperTest {

    String FIRST_NAME = "John";
    String LAST_NAME = "Kowalski";

    InstructorMapper instructorMapper = InstructorMapper.INSTANCE;

    @Test
    void DTOtoObject() {
        Instructor instructor =
                Instructor.builder()
                        .firstName(FIRST_NAME)
                        .lastName(LAST_NAME)
                        .build();

        InstructorDTO dto = instructorMapper.toDto(instructor);

        assertEquals(instructor.getFirstName(), dto.getFirstName());
        assertEquals(instructor.getLastName(), dto.getLastName());
    }

    @Test
    void ObjectToDTO() {
        InstructorDTO dto = InstructorDTO
                .builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .build();

        Instructor instructor = instructorMapper.toInstructor(dto);

        assertEquals(dto.getFirstName(), instructor.getFirstName());
        assertEquals(dto.getLastName(), instructor.getLastName());
    }
}
