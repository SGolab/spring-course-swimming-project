package com.example.sgswimming.web.mappers;

import com.example.sgswimming.web.DTOs.SwimmerFatDto;
import com.example.sgswimming.web.DTOs.SwimmerSkinnyDto;
import com.example.sgswimming.model.Lesson;
import com.example.sgswimming.model.Swimmer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SwimmerMapper {

    private static SwimmerMapper INSTANCE;

    private SwimmerMapper() {
    }

    public static SwimmerMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SwimmerMapper();
        }
        return INSTANCE;
    }

    SwimmerMapper.Fat fatMapper = Mappers.getMapper(SwimmerMapper.Fat.class);
    SwimmerMapper.Skinny skinnyMapper = Mappers.getMapper(SwimmerMapper.Skinny.class);

    public SwimmerFatDto toFatDto(Swimmer swimmer) {
        return fatMapper.toFatDto(swimmer);
    }

    public Swimmer fromFatToSwimmer(SwimmerFatDto dto) {
        return fatMapper.fromFatToSwimmer(dto);
    }

    public SwimmerSkinnyDto toSkinnyDto(Swimmer swimmer) {
        return skinnyMapper.toDto(swimmer);
    }

    public Swimmer fromSkinnyToSwimmer(SwimmerSkinnyDto dto) {
        return skinnyMapper.fromSkinnyToSwimmer(dto);
    }

    @Mapper(uses = LessonMapper.Skinny.class)
    interface Fat {
        SwimmerMapper.Fat INSTANCE = Mappers.getMapper(SwimmerMapper.Fat.class);

        SwimmerFatDto toFatDto(Swimmer swimmer);
        Swimmer fromFatToSwimmer(SwimmerFatDto dto);
    }

    @Mapper
    interface Skinny {
        SwimmerMapper.Skinny INSTANCE = Mappers.getMapper(SwimmerMapper.Skinny.class);

        @Mapping(source = "lessons", target = "lessonIds", qualifiedByName = "lessonsToLessonIds")
        SwimmerSkinnyDto toDto(Swimmer swimmer);
        Swimmer fromSkinnyToSwimmer(SwimmerSkinnyDto dto);

        @Named("lessonsToLessonIds")
        static List<Long> lessonsToLessonIds(List<Lesson> lessons) {
            return lessons.stream()
                    .map(Lesson::getId)
                    .collect(Collectors.toList());
        }
    }
}
