package com.example.sgswimming.mappers;

import com.example.sgswimming.DTOs.LessonDTO;
import com.example.sgswimming.model.Lesson;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LessonMapperTest {

    String DESCRIPTION = "Desc";
    LocalDateTime LOCAL_DATE_TIME = LocalDateTime.MAX;

    LessonMapper lessonMapper = LessonMapper.INSTANCE;

    @Test
    void DTOtoObject() {
        Lesson lesson =
                Lesson.builder()
                        .description(DESCRIPTION)
                        .localDateTime(LOCAL_DATE_TIME)
                        .build();

        LessonDTO dto = lessonMapper.toDto(lesson);

        assertEquals(lesson.getDescription(), dto.getDescription());
        assertEquals(lesson.getLocalDateTime(), dto.getLocalDateTime());
    }

    @Test
    void ObjectToDTO() {
        LessonDTO dto = LessonDTO
                .builder()
                .description(DESCRIPTION)
                .localDateTime(LOCAL_DATE_TIME)
                .build();

        Lesson lesson = lessonMapper.toLesson(dto);

        assertEquals(dto.getDescription(), lesson.getDescription());
        assertEquals(dto.getLocalDateTime(), lesson.getLocalDateTime());
    }
}
