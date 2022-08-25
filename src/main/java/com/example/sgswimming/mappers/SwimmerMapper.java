package com.example.sgswimming.mappers;

import com.example.sgswimming.DTOs.SwimmerDTO;
import com.example.sgswimming.model.Lesson;
import com.example.sgswimming.model.Swimmer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(uses = LessonMapper.Skinny.class)
public interface SwimmerMapper {
    SwimmerMapper INSTANCE = Mappers.getMapper(SwimmerMapper.class);

    SwimmerDTO toDto(Swimmer swimmer);
    Swimmer toSwimmer(SwimmerDTO dto);

    @Mapper
    interface Skinny {
        SwimmerMapper.Skinny INSTANCE = Mappers.getMapper(SwimmerMapper.Skinny.class);

        @Mapping(source = "lessons", target = "lessonIds", qualifiedByName = "lessonsToLessonIds")
        SwimmerDTO.Skinny toDto(Swimmer swimmer);

        @Named("lessonsToLessonIds")
        static List<Long> lessonsToLessonIds(List<Lesson> lessons) {
            return lessons.stream()
                    .map(Lesson::getId)
                    .collect(Collectors.toList());
        }
    }
}
