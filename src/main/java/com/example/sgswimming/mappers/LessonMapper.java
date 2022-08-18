package com.example.sgswimming.mappers;

import com.example.sgswimming.DTOs.InstructorDTO;
import com.example.sgswimming.DTOs.LessonDTO;
import com.example.sgswimming.model.Instructor;
import com.example.sgswimming.model.Lesson;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LessonMapper {
    LessonMapper INSTANCE = Mappers.getMapper(LessonMapper.class);
    LessonDTO toDto(Lesson lesson);
    Lesson toInstructor(LessonDTO dto);
}
