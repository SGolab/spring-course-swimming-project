package com.example.sgswimming.web.mappers;

import com.example.sgswimming.web.DTOs.LessonFatDto;
import com.example.sgswimming.web.DTOs.LessonSkinnyDto;
import com.example.sgswimming.web.config.JsonDateMappingConfig;
import com.example.sgswimming.model.Instructor;
import com.example.sgswimming.model.Lesson;
import com.example.sgswimming.model.Swimmer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class LessonMapper {

    private static LessonMapper INSTANCE;

    private LessonMapper() {
    }

    public static LessonMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LessonMapper();
        }
        return INSTANCE;
    }

    LessonMapper.Fat fatMapper = Mappers.getMapper(LessonMapper.Fat.class);
    LessonMapper.Skinny skinnyMapper = Mappers.getMapper(LessonMapper.Skinny.class);

    public LessonFatDto toFatDto(Lesson lesson) {
        return fatMapper.toFatDto(lesson);
    }

    public Lesson fromFatToLesson(LessonFatDto dto) {
        return fatMapper.fromFatToLesson(dto);
    }

    public LessonSkinnyDto toSkinnyDto(Lesson lesson) {
        return skinnyMapper.toDto(lesson);
    }

    public Lesson fromSkinnyToLesson(LessonSkinnyDto dto) {
        return skinnyMapper.fromSkinnyToLesson(dto);
    }


    @Mapper(uses = {InstructorMapper.Skinny.class, SwimmerMapper.Skinny.class})
    interface Fat {
        LessonMapper.Fat INSTANCE = Mappers.getMapper(LessonMapper.Fat.class);

        LessonFatDto toFatDto(Lesson lesson);
        Lesson fromFatToLesson(LessonFatDto dto);
    }

    @Mapper
    interface Skinny {
        LessonMapper.Skinny INSTANCE = Mappers.getMapper(LessonMapper.Skinny.class);

        @Mappings({
                @Mapping(source = "instructor", target = "instructorId", qualifiedByName = "instructorToId"),
                @Mapping(source = "swimmers", target = "swimmerIds", qualifiedByName = "swimmersToSwimmerIds"),
                @Mapping(target = "localDateTime", dateFormat = "HH:mm dd.MM.yyyy")
        })
        LessonSkinnyDto toDto(Lesson lesson);

        @Mapping(target = "localDateTime", dateFormat = "HH:mm dd.MM.yyyy")
        Lesson fromSkinnyToLesson(LessonSkinnyDto dto);

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

        @Named("localDateTimeToString")
        static String localDateTimeToString(LocalDateTime localDateTime) {
            return localDateTime.format(DateTimeFormatter.ofPattern(JsonDateMappingConfig.DATE_TIME_FORMAT));
        }
    }
}
