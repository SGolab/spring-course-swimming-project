package com.example.sgswimming.mappers;

import com.example.sgswimming.DTOs.LessonDTO;
import com.example.sgswimming.model.Instructor;
import com.example.sgswimming.model.Lesson;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LessonMapperTest {

    String DESCRIPTION = "Desc";
    LocalDateTime LOCAL_DATE_TIME = LocalDateTime.MAX;
    Long INSTRUCTOR_ID = 1L;
    Instructor INSTRUCTOR = Instructor.builder().id(INSTRUCTOR_ID).build();

    LessonMapper mapper = LessonMapper.INSTANCE;
    LessonMapper.Skinny skinnyMapper = LessonMapper.Skinny.INSTANCE;

    @Test
    void objectToDTO() {
        Lesson lesson =
                Lesson.builder()
                        .description(DESCRIPTION)
                        .localDateTime(LOCAL_DATE_TIME)
                        .instructor(INSTRUCTOR)
                        .build();

        LessonDTO dto = mapper.toDto(lesson);

        assertEquals(lesson.getDescription(), dto.getDescription());
        assertEquals(lesson.getLocalDateTime(), dto.getLocalDateTime());
        assertEquals(INSTRUCTOR_ID, dto.getInstructor().getId());
        assertNotNull(dto.getSwimmers());
    }

    @Test
    void objectToSkinnyDTO() {
        Lesson lesson =
                Lesson.builder()
                        .description(DESCRIPTION)
                        .localDateTime(LOCAL_DATE_TIME)
                        .instructor(INSTRUCTOR)
                        .build();

        LessonDTO.Skinny dto = skinnyMapper.toDto(lesson);

        assertEquals(lesson.getDescription(), dto.getDescription());
        assertEquals(lesson.getLocalDateTime(), dto.getLocalDateTime());
        assertEquals(INSTRUCTOR_ID, dto.getInstructorId());
        assertNotNull(dto.getSwimmerIds());
    }

    @Test
    void DTOtoObject() {
        LessonDTO dto = LessonDTO
                .builder()
                .description(DESCRIPTION)
                .localDateTime(LOCAL_DATE_TIME)
                .build();

        Lesson lesson = mapper.toLesson(dto);

        assertEquals(dto.getDescription(), lesson.getDescription());
        assertEquals(dto.getLocalDateTime(), lesson.getLocalDateTime());
        assertNotNull(lesson.getSwimmers());
    }
}
