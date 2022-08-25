package com.example.sgswimming.mappers;


import com.example.sgswimming.DTOs.InstructorDTO;
import com.example.sgswimming.model.Instructor;
import com.example.sgswimming.model.Lesson;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(uses = LessonMapper.Skinny.class)
public interface InstructorMapper {
    InstructorMapper INSTANCE = Mappers.getMapper(InstructorMapper.class);

    InstructorDTO toDto(Instructor instructor);
    Instructor toInstructor(InstructorDTO dto);

    @Mapper
    interface Skinny {
        InstructorMapper.Skinny INSTANCE = Mappers.getMapper(InstructorMapper.Skinny.class);

        @Mapping(source = "lessons", target = "lessonIds", qualifiedByName = "lessonsToLessonIds")
        InstructorDTO.Skinny toDto(Instructor instructor);

        @Named("lessonsToLessonIds")
        static List<Long> lessonsToLessonIds(List<Lesson> lessons) {
            return lessons.stream()
                    .map(Lesson::getId)
                    .collect(Collectors.toList());
        }
    }
}
