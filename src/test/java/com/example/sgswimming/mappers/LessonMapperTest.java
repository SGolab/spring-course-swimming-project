package com.example.sgswimming.mappers;

import com.example.sgswimming.model.Instructor;
import com.example.sgswimming.model.Lesson;
import com.example.sgswimming.model.Swimmer;
import com.example.sgswimming.web.DTOs.read.LessonReadDto;
import com.example.sgswimming.web.DTOs.save.LessonSaveDto;
import com.example.sgswimming.web.DTOs.update.LessonUpdateDto;
import com.example.sgswimming.web.config.JsonDateMappingConfig;
import com.example.sgswimming.web.mappers.LessonMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LessonMapperTest {

    String DESCRIPTION = "Desc";
    Integer ADVANCE_LEVEL = 2;
    LocalDateTime LOCAL_DATE_TIME = LocalDateTime.now();
    String LOCAL_DATE_TIME_STRING = LOCAL_DATE_TIME.format(DateTimeFormatter.ofPattern(JsonDateMappingConfig.DATE_TIME_FORMAT));

    LessonMapper mapper = LessonMapper.getInstance();

    Lesson lesson;
    LessonSaveDto lessonSaveDto;
    LessonUpdateDto lessonUpdateDto;

    @BeforeEach
    void setUp() {
        lesson = new Lesson();
        lesson.setId(1L);
        lesson.setDescription(DESCRIPTION);
        lesson.setLocalDateTime(LOCAL_DATE_TIME);
        lesson.setAdvanceLevel(ADVANCE_LEVEL);
        lesson.setInstructor(new Instructor());
        lesson.setSwimmers(List.of(
                new Swimmer(),
                new Swimmer(),
                new Swimmer()));

        lessonSaveDto = new LessonSaveDto();
        lessonSaveDto.setDescription(DESCRIPTION);
        lessonSaveDto.setLocalDateTime(LOCAL_DATE_TIME_STRING);
        lessonSaveDto.setAdvanceLevel(ADVANCE_LEVEL);

        lessonUpdateDto = new LessonUpdateDto();
        lessonUpdateDto.setId(1L);
        lessonUpdateDto.setDescription(DESCRIPTION);
        lessonUpdateDto.setLocalDateTime(LOCAL_DATE_TIME_STRING);
        lessonUpdateDto.setAdvanceLevel(ADVANCE_LEVEL);
    }

    @Test
    void toReadDto() {
        LessonReadDto lessonReadDto = mapper.toReadDto(lesson);

        assertEquals(DESCRIPTION, lessonReadDto.getDescription());
        assertEquals(ADVANCE_LEVEL, lessonReadDto.getAdvanceLevel());
        assertNotNull(lessonReadDto.getLocalDateTime());
        assertNotNull(lessonReadDto.getInstructor());
        assertFalse(lessonReadDto.getSwimmers().isEmpty());
    }

    @Test
    void fromSaveDtoToLesson() {
        Lesson lesson = mapper.fromSaveDtoToLesson(lessonSaveDto);

        assertEquals(DESCRIPTION, lesson.getDescription());
        assertEquals(ADVANCE_LEVEL, lesson.getAdvanceLevel());
        assertNotNull(lesson.getLocalDateTime());

        assertNull(lesson.getInstructor());
        assertTrue(lesson.getSwimmers().isEmpty());
    }

    @Test
    void fromUpdateDtoToLesson() {
        Lesson lesson = mapper.fromUpdateDtoToLesson(lessonUpdateDto);

        assertEquals(1L, lesson.getId());
        assertEquals(DESCRIPTION, lesson.getDescription());
        assertEquals(ADVANCE_LEVEL, lesson.getAdvanceLevel());
        assertNotNull(lesson.getLocalDateTime());

        assertNull(lesson.getInstructor());
        assertTrue(lesson.getSwimmers().isEmpty());
    }
}
