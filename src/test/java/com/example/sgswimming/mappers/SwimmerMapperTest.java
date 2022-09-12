package com.example.sgswimming.mappers;

import com.example.sgswimming.web.DTOs.SwimmerFatDto;
import com.example.sgswimming.web.DTOs.SwimmerSkinnyDto;
import com.example.sgswimming.model.Swimmer;
import com.example.sgswimming.web.mappers.SwimmerMapper;
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

    SwimmerMapper mapper = SwimmerMapper.getInstance();

    @Test
    void objectToDTO() {
        SwimmerFatDto dto = mapper.toFatDto(SWIMMER);

        assertEquals(FIRST_NAME, dto.getFirstName());
        assertEquals(LAST_NAME, dto.getLastName());
        assertNotNull(dto.getLessons());
    }

    @Test
    void DTOtoObject() {
        SwimmerFatDto dto = SwimmerFatDto
                .builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .build();

        Swimmer swimmer = mapper.fromFatToSwimmer(dto);

        assertEquals(swimmer.getFirstName(), dto.getFirstName());
        assertEquals(swimmer.getLastName(), dto.getLastName());
        assertNotNull(swimmer.getLessons());
    }

    @Test
    void objectToSkinnyDTO() {
        SwimmerSkinnyDto dto = mapper.toSkinnyDto(SWIMMER);

        assertEquals(FIRST_NAME, dto.getFirstName());
        assertEquals(LAST_NAME, dto.getLastName());
        assertNotNull(dto.getLessonIds());
    }

    @Test
    void skinnyDtoToObject() {
        SwimmerSkinnyDto dto = SwimmerSkinnyDto
                .builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .build();

        Swimmer swimmer = mapper.fromSkinnyToSwimmer(dto);

        assertEquals(swimmer.getFirstName(), dto.getFirstName());
        assertEquals(swimmer.getLastName(), dto.getLastName());
        assertNotNull(swimmer.getLessons());
    }
}
