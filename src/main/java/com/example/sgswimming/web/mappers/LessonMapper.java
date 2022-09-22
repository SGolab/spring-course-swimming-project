package com.example.sgswimming.web.mappers;

import com.example.sgswimming.model.Lesson;
import com.example.sgswimming.web.DTOs.read.LessonReadDto;
import com.example.sgswimming.web.DTOs.save.LessonSaveOrUpdateDto;
import com.example.sgswimming.web.config.JsonDateMappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
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

    LessonMapper.Read readMapper = Mappers.getMapper(LessonMapper.Read.class);
    SaveOrUpdate saveOrUpdateMapper = Mappers.getMapper(SaveOrUpdate.class);

    public LessonReadDto toReadDto(Lesson lesson) {
        return readMapper.toReadDto(lesson);
    }

    public Lesson fromSaveOrUpdateDtoToLesson(LessonSaveOrUpdateDto dto) {
        return saveOrUpdateMapper.fromUpdateDtoToLesson(dto);
    }

    @Mapper(uses = {InstructorMapper.Read.class, SwimmerMapper.Read.class})
    interface Read {
        LessonMapper.Read INSTANCE = Mappers.getMapper(LessonMapper.Read.class);
        LessonReadDto toReadDto(Lesson lesson);
    }

    @Mapper(uses = SaveOrUpdate.class)
    interface SaveOrUpdate {
        SaveOrUpdate INSTANCE = Mappers.getMapper(SaveOrUpdate.class);
        Lesson fromUpdateDtoToLesson(LessonSaveOrUpdateDto dto);

        static LocalDateTime map(String value) {
            return LocalDateTime.parse(value, DateTimeFormatter.ofPattern(JsonDateMappingConfig.DATE_TIME_FORMAT));
        }
    }
}
