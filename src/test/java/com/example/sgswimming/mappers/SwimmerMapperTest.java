package com.example.sgswimming.mappers;

import com.example.sgswimming.model.Swimmer;
import com.example.sgswimming.model.Lesson;
import com.example.sgswimming.web.DTOs.read.SwimmerReadDto;
import com.example.sgswimming.web.DTOs.save.SwimmerSaveDto;
import com.example.sgswimming.web.DTOs.update.SwimmerUpdateDto;
import com.example.sgswimming.web.mappers.SwimmerMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SwimmerMapperTest {

    Long ID = 1L;
    String FIRST_NAME = "John";
    String LAST_NAME = "Kowalski";
    Swimmer SWIMMER = Swimmer.builder()
            .firstName(FIRST_NAME)
            .lastName(LAST_NAME)
            .build();

    SwimmerMapper mapper = SwimmerMapper.getInstance();

    Swimmer swimmer;
    SwimmerSaveDto swimmerSaveDto;
    SwimmerUpdateDto swimmerUpdateDto;

    @BeforeEach
    void setUp() {
        swimmer = new Swimmer();
        swimmer.setId(1L);
        swimmer.setFirstName(FIRST_NAME);
        swimmer.setLastName(LAST_NAME);

        swimmer.setLessons(List.of(
                new Lesson(),
                new Lesson(),
                new Lesson()
        ));

        swimmerSaveDto = new SwimmerSaveDto();
        swimmerSaveDto.setFirstName(FIRST_NAME);
        swimmerSaveDto.setLastName(LAST_NAME);

        swimmerUpdateDto = new SwimmerUpdateDto();
        swimmerUpdateDto.setId(1L);
        swimmerUpdateDto.setFirstName(FIRST_NAME);
        swimmerUpdateDto.setLastName(LAST_NAME);
        swimmerUpdateDto.setLessons(Set.of(1L, 2L, 3L));
    }

    @Test
    void toReadDto() {
        SwimmerReadDto swimmerReadDto = mapper.toReadDto(swimmer);

        assertEquals(ID, swimmerReadDto.getId());
        assertEquals(FIRST_NAME, swimmerReadDto.getFirstName());
        assertEquals(LAST_NAME, swimmerReadDto.getLastName());
        assertFalse(swimmerReadDto.getLessons().isEmpty());
    }

    @Test
    void fromSaveDtoToSwimmer() {
        Swimmer swimmer = mapper.fromSaveDtoToSwimmer(swimmerSaveDto);

        assertEquals(FIRST_NAME, swimmer.getFirstName());
        assertEquals(LAST_NAME, swimmer.getLastName());
        assertTrue(swimmer.getLessons().isEmpty());
    }

    @Test
    void fromUpdateDtoToSwimmer() {
        Swimmer swimmer = mapper.fromUpdateDtoToSwimmer(swimmerUpdateDto);

        assertEquals(ID, swimmer.getId());
        assertEquals(FIRST_NAME, swimmer.getFirstName());
        assertEquals(LAST_NAME, swimmer.getLastName());
        assertTrue(swimmer.getLessons().isEmpty());
    }
}
