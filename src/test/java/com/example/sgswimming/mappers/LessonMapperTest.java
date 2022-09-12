package com.example.sgswimming.mappers;

import com.example.sgswimming.web.DTOs.LessonFatDto;
import com.example.sgswimming.web.DTOs.LessonSkinnyDto;
import com.example.sgswimming.web.config.JsonDateMappingConfig;
import com.example.sgswimming.model.Instructor;
import com.example.sgswimming.model.Lesson;
import com.example.sgswimming.web.mappers.LessonMapper;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LessonMapperTest {

    String DESCRIPTION = "Desc";
    LocalDateTime LOCAL_DATE_TIME = LocalDateTime.now();
    String LOCAL_DATE_TIME_STRING = LOCAL_DATE_TIME.format(DateTimeFormatter.ofPattern(JsonDateMappingConfig.DATE_TIME_FORMAT));
    Long INSTRUCTOR_ID = 1L;
    Instructor INSTRUCTOR = Instructor.builder().id(INSTRUCTOR_ID).build();

    LessonMapper mapper = LessonMapper.getInstance();

    @Test
    void objectToDTO() {
        Lesson lesson =
                Lesson.builder()
                        .description(DESCRIPTION)
                        .localDateTime(LOCAL_DATE_TIME)
                        .instructor(INSTRUCTOR)
                        .build();

        LessonFatDto dto = mapper.toFatDto(lesson);

        assertEquals(DESCRIPTION, dto.getDescription());
        assertEquals(LOCAL_DATE_TIME, dto.getLocalDateTime());
        assertEquals(INSTRUCTOR_ID, dto.getInstructor().getId());
        assertNotNull(dto.getSwimmers());
    }

    @Test
    void DTOtoObject() {
        LessonFatDto dto = LessonFatDto
                .builder()
                .description(DESCRIPTION)
                .localDateTime(LOCAL_DATE_TIME)
                .build();

        Lesson lesson = mapper.fromFatToLesson(dto);

        assertEquals(DESCRIPTION, lesson.getDescription());
        assertEquals(LOCAL_DATE_TIME, lesson.getLocalDateTime());
        assertNotNull(lesson.getSwimmers());
    }

    @Test
    void objectToSkinnyDTO() {
        Lesson lesson =
                Lesson.builder()
                        .description(DESCRIPTION)
                        .localDateTime(LOCAL_DATE_TIME)
                        .instructor(INSTRUCTOR)
                        .build();

        LessonSkinnyDto dto = mapper.toSkinnyDto(lesson);

        assertEquals(DESCRIPTION, dto.getDescription());
        assertEquals(LOCAL_DATE_TIME_STRING, dto.getLocalDateTime());
        assertEquals(INSTRUCTOR_ID, dto.getInstructorId());
        assertNotNull(dto.getSwimmerIds());
    }

    @Test
    void skinnyDtoToObject() {
        LessonSkinnyDto dto = LessonSkinnyDto
                .builder()
                .description(DESCRIPTION)
                .localDateTime(LOCAL_DATE_TIME_STRING)
                .build();

        Lesson lesson = mapper.fromSkinnyToLesson(dto);

        assertEquals(DESCRIPTION, lesson.getDescription());
        assertEquals(LOCAL_DATE_TIME_STRING, lesson.getLocalDateTime().format(DateTimeFormatter.ofPattern(JsonDateMappingConfig.DATE_TIME_FORMAT)));
        assertNotNull(lesson.getSwimmers());
    }
}
