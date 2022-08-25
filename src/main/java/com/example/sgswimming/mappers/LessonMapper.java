package com.example.sgswimming.mappers;

import com.example.sgswimming.DTOs.LessonDTO;
import com.example.sgswimming.model.Lesson;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LessonMapper {
    LessonMapper INSTANCE = Mappers.getMapper(LessonMapper.class);

    LessonDTO toDto(Lesson lesson, @Context CycleAvoidingMappingContext context);
    Lesson toLesson(LessonDTO dto, @Context CycleAvoidingMappingContext context);

    @DoIgnore
    default LessonDTO toDto(Lesson lesson) {
        return toDto(lesson, new CycleAvoidingMappingContext());
    }

    @DoIgnore
    default Lesson toLesson(LessonDTO dto) {
        return toLesson(dto, new CycleAvoidingMappingContext());
    }
}
