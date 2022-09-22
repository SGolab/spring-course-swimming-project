package com.example.sgswimming.mappers;

import com.example.sgswimming.model.Instructor;
import com.example.sgswimming.model.Lesson;
import com.example.sgswimming.model.Swimmer;
import com.example.sgswimming.web.DTOs.read.LessonReadDto;
import com.example.sgswimming.web.DTOs.save.LessonSaveOrUpdateDto;
import com.example.sgswimming.web.config.JsonDateMappingConfig;
import com.example.sgswimming.web.mappers.LessonMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class LessonMapperTest {

    String DESCRIPTION = "Desc";
    LocalDateTime LOCAL_DATE_TIME = LocalDateTime.now();
    String LOCAL_DATE_TIME_STRING = LOCAL_DATE_TIME.format(DateTimeFormatter.ofPattern(JsonDateMappingConfig.DATE_TIME_FORMAT));

    LessonMapper mapper = LessonMapper.getInstance();

    Lesson lesson;
    LessonSaveOrUpdateDto lessonSaveOrUpdateDto;

    @BeforeEach
    void setUp() {
        lesson = new Lesson();
        lesson.setId(1L);
        lesson.setDescription(DESCRIPTION);
        lesson.setLocalDateTime(LOCAL_DATE_TIME);
        lesson.setInstructor(new Instructor());
        lesson.setSwimmers(List.of(
                new Swimmer(),
                new Swimmer(),
                new Swimmer()));

        lessonSaveOrUpdateDto = new LessonSaveOrUpdateDto();
        lessonSaveOrUpdateDto.setId(1L);
        lessonSaveOrUpdateDto.setDescription(DESCRIPTION);
        lessonSaveOrUpdateDto.setLocalDateTime(LOCAL_DATE_TIME_STRING);
    }

    @Test
    void toReadDto() {
        LessonReadDto lessonReadDto = mapper.toReadDto(lesson);

        assertEquals(DESCRIPTION, lessonReadDto.getDescription());
        assertNotNull(lessonReadDto.getLocalDateTime());
        assertNotNull(lessonReadDto.getInstructor());
        assertFalse(lessonReadDto.getSwimmers().isEmpty());
    }

    @Test
    void fromSaveOrUpdateDtoToLesson() {
        Lesson lesson = mapper.fromSaveOrUpdateDtoToLesson(lessonSaveOrUpdateDto);

        assertEquals(DESCRIPTION, lesson.getDescription());
        assertNotNull(lesson.getLocalDateTime());

        assertNull(lesson.getInstructor());
        assertTrue(lesson.getSwimmers().isEmpty());
    }
}
