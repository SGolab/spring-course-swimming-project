package com.example.sgswimming.mappers;

import com.example.sgswimming.DTOs.SwimmerDTO;
import com.example.sgswimming.model.Swimmer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SwimmerMapperTest {

    String FIRST_NAME = "John";
    String LAST_NAME = "Kowalski";
    Swimmer SWIMMER = Swimmer.builder()
            .firstName(FIRST_NAME)
            .lastName(LAST_NAME)
            .build();

    SwimmerMapper mapper = SwimmerMapper.INSTANCE;
    SwimmerMapper.Skinny skinnyMapper = SwimmerMapper.Skinny.INSTANCE;

    @Test
    void objectToDTO() {
        SwimmerDTO dto = mapper.toDto(SWIMMER);

        assertEquals(FIRST_NAME, dto.getFirstName());
        assertEquals(LAST_NAME, dto.getLastName());
        assertNotNull(dto.getLessons());
    }

    @Test
    void objectToSkinnyDTO() {
        SwimmerDTO.Skinny dto = skinnyMapper.toDto(SWIMMER);

        assertEquals(FIRST_NAME, dto.getFirstName());
        assertEquals(LAST_NAME, dto.getLastName());
        assertNotNull(dto.getLessonIds());
    }

    @Test
    void DTOtoObject() {
        SwimmerDTO dto = SwimmerDTO
                .builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .build();

        Swimmer swimmer = mapper.toSwimmer(dto);

        assertEquals(swimmer.getFirstName(), dto.getFirstName());
        assertEquals(swimmer.getLastName(), dto.getLastName());
        assertNotNull(swimmer.getLessons());
    }

}
