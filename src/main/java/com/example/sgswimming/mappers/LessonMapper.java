package com.example.sgswimming.mappers;

import com.example.sgswimming.DTOs.LessonDTO;
import com.example.sgswimming.model.Instructor;
import com.example.sgswimming.model.Lesson;
import com.example.sgswimming.model.Swimmer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(uses = {InstructorMapper.Skinny.class, SwimmerMapper.Skinny.class})
public interface LessonMapper {
    LessonMapper INSTANCE = Mappers.getMapper(LessonMapper.class);

    LessonDTO toDto(Lesson lesson);
    Lesson toLesson(LessonDTO dto);

    @Mapper
    interface Skinny {
        LessonMapper.Skinny INSTANCE = Mappers.getMapper(LessonMapper.Skinny.class);

        @Mappings({
                @Mapping(source = "instructor", target = "instructorId", qualifiedByName = "instructorToId"),
                @Mapping(source = "swimmers", target = "swimmerIds", qualifiedByName = "swimmersToSwimmerIds")})
        LessonDTO.Skinny toDto(Lesson lesson);

        @Named("instructorToId")
        static Long instructorToId(Instructor instructor) {
            return instructor.getId();
        }

        @Named("swimmersToSwimmerIds")
        static List<Long> swimmersToSwimmerIds(List<Swimmer> swimmers) {
            return swimmers.stream()
                    .map(Swimmer::getId)
                    .collect(Collectors.toList());
        }
    }
}
