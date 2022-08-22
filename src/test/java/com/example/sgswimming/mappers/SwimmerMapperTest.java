package com.example.sgswimming.mappers;

import com.example.sgswimming.DTOs.SwimmerDTO;
import com.example.sgswimming.model.Swimmer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SwimmerMapperTest {

    String FIRST_NAME = "John";
    String LAST_NAME = "Kowalski";

    SwimmerMapper swimmerMapper = SwimmerMapper.INSTANCE;

    @Test
    void DTOtoObject() {
        Swimmer swimmer =
                Swimmer.builder()
                        .firstName(FIRST_NAME)
                        .lastName(LAST_NAME)
                        .build();

        SwimmerDTO dto = swimmerMapper.toDto(swimmer);

        assertEquals(swimmer.getFirstName(), dto.getFirstName());
        assertEquals(swimmer.getLastName(), dto.getLastName());
    }

    @Test
    void ObjectToDTO() {
        SwimmerDTO dto = SwimmerDTO
                .builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .build();

        Swimmer swimmer = swimmerMapper.toSwimmer(dto);

        assertEquals(dto.getFirstName(), swimmer.getFirstName());
        assertEquals(dto.getLastName(), swimmer.getLastName());
    }

}
