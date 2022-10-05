package com.example.sgswimming.mappers;

import com.example.sgswimming.model.Swimmer;
import com.example.sgswimming.model.Lesson;
import com.example.sgswimming.web.DTOs.read.SwimmerReadDto;
import com.example.sgswimming.web.DTOs.save.SwimmerSaveDto;
import com.example.sgswimming.web.DTOs.update.SwimmerUpdateDto;
import com.example.sgswimming.web.mappers.SwimmerMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SwimmerMapperTest {

    Long ID = 1L;
    String FIRST_NAME = "John";
    String LAST_NAME = "Kowalski";
    Integer ADVANCE_LEVEL = 2;
    String BIRTH_DATE_STRING = "01.01.2000";


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
        swimmer.setAdvanceLevel(ADVANCE_LEVEL);
        swimmer.setBirthDate(LocalDate.now());

        swimmer.setLessons(Set.of(
                new Lesson()
        ));

        swimmerSaveDto = new SwimmerSaveDto();
        swimmerSaveDto.setFirstName(FIRST_NAME);
        swimmerSaveDto.setLastName(LAST_NAME);
        swimmerSaveDto.setAdvanceLevel(ADVANCE_LEVEL);
        swimmerSaveDto.setBirthDate(BIRTH_DATE_STRING);

        swimmerUpdateDto = new SwimmerUpdateDto();
        swimmerUpdateDto.setId(1L);
        swimmerUpdateDto.setFirstName(FIRST_NAME);
        swimmerUpdateDto.setLastName(LAST_NAME);
        swimmerUpdateDto.setAdvanceLevel(ADVANCE_LEVEL);
        swimmerUpdateDto.setBirthDate(BIRTH_DATE_STRING);
        swimmerUpdateDto.setLessons(Set.of(1L));
    }

    @Test
    void toReadDto() {
        SwimmerReadDto swimmerReadDto = mapper.toReadDto(swimmer);

        assertEquals(ID, swimmerReadDto.getId());
        assertEquals(FIRST_NAME, swimmerReadDto.getFirstName());
        assertEquals(LAST_NAME, swimmerReadDto.getLastName());
        assertEquals(ADVANCE_LEVEL, swimmerReadDto.getAdvanceLevel());

        assertNotNull(swimmerReadDto.getBirthDate());
        assertFalse(swimmerReadDto.getLessons().isEmpty());
    }

    @Test
    void fromSaveDtoToSwimmer() {
        Swimmer swimmer = mapper.fromSaveDtoToSwimmer(swimmerSaveDto);

        assertEquals(FIRST_NAME, swimmer.getFirstName());
        assertEquals(LAST_NAME, swimmer.getLastName());
        assertEquals(ADVANCE_LEVEL, swimmer.getAdvanceLevel());

        assertNotNull(swimmer.getBirthDate());
        assertTrue(swimmer.getLessons().isEmpty());
    }

    @Test
    void fromUpdateDtoToSwimmer() {
        Swimmer swimmer = mapper.fromUpdateDtoToSwimmer(swimmerUpdateDto);

        assertEquals(ID, swimmer.getId());
        assertEquals(FIRST_NAME, swimmer.getFirstName());
        assertEquals(LAST_NAME, swimmer.getLastName());
        assertEquals(ADVANCE_LEVEL, swimmer.getAdvanceLevel());

        assertNotNull(swimmer.getBirthDate());
        assertTrue(swimmer.getLessons().isEmpty());
    }
}
