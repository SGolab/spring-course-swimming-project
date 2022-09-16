package com.example.sgswimming.web.mappers;


import com.example.sgswimming.model.Instructor;
import com.example.sgswimming.model.Lesson;
import com.example.sgswimming.web.DTOs.InstructorFatDto;
import com.example.sgswimming.web.DTOs.InstructorSkinnyDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class InstructorMapper {

    private static InstructorMapper INSTANCE;

    private InstructorMapper() {
    }

    public static InstructorMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new InstructorMapper();
        }
        return INSTANCE;
    }

    InstructorMapper.Fat fatMapper = Mappers.getMapper(InstructorMapper.Fat.class);
    InstructorMapper.Skinny skinnyMapper = Mappers.getMapper(InstructorMapper.Skinny.class);

    public InstructorFatDto toFatDto(Instructor instructor) {
        return fatMapper.toFatDto(instructor);
    }

    public Instructor fromFatToInstructor(InstructorFatDto dto) {
        return fatMapper.fromFatToInstructor(dto);
    }

    public InstructorSkinnyDto toSkinnyDto(Instructor instructor) {
        return skinnyMapper.toSkinnyDto(instructor);
    }

    public Instructor fromSkinnyToInstructor(InstructorSkinnyDto dto) {
        return skinnyMapper.fromSkinnyToInstructor(dto);
    }

    @Mapper(uses = LessonMapper.Skinny.class)
    interface Fat {
        InstructorMapper.Fat INSTANCE = Mappers.getMapper(InstructorMapper.Fat.class);

        InstructorFatDto toFatDto(Instructor instructor);

        Instructor fromFatToInstructor(InstructorFatDto dto);
    }

    @Mapper
    interface Skinny {
        InstructorMapper.Skinny INSTANCE = Mappers.getMapper(InstructorMapper.Skinny.class);

        @Mapping(source = "lessons", target = "lessonIds", qualifiedByName = "lessonsToLessonIds")
        InstructorSkinnyDto toSkinnyDto(Instructor instructor);

        Instructor fromSkinnyToInstructor(InstructorSkinnyDto dto);

        @Named("lessonsToLessonIds")
        static List<Long> lessonsToLessonIds(List<Lesson> lessons) {
            return lessons.stream()
                    .map(Lesson::getId)
                    .collect(Collectors.toList());
        }
    }
}
