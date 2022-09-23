package com.example.sgswimming.web.mappers;

import com.example.sgswimming.model.Lesson;
import com.example.sgswimming.web.DTOs.read.LessonReadDto;
import com.example.sgswimming.web.DTOs.save.LessonSaveDto;
import com.example.sgswimming.web.DTOs.update.LessonUpdateDto;
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
    LessonMapper.Save saveMapper = Mappers.getMapper(LessonMapper.Save.class);
    LessonMapper.Update updateMapper = Mappers.getMapper(LessonMapper.Update.class);

    public LessonReadDto toReadDto(Lesson lesson) {
        return readMapper.toReadDto(lesson);
    }

    public Lesson fromSaveDtoToLesson(LessonSaveDto lessonSaveDto) {
        return saveMapper.fromSaveDtoToLesson(lessonSaveDto);
    }

    public Lesson fromUpdateDtoToLesson(LessonUpdateDto lessonUpdateDto) {
        return updateMapper.fromUpdateDtoToLesson(lessonUpdateDto);
    }

    @Mapper(uses = {InstructorMapper.Read.class, SwimmerMapper.Read.class})
    interface Read {
        LessonMapper.Read INSTANCE = Mappers.getMapper(LessonMapper.Read.class);
        LessonReadDto toReadDto(Lesson lesson);
    }

    @Mapper
    interface Save {
        Save INSTANCE = Mappers.getMapper(Save.class);
        Lesson fromSaveDtoToLesson(LessonSaveDto dto);

        static LocalDateTime map(String value) {
            return LocalDateTime.parse(value, DateTimeFormatter.ofPattern(JsonDateMappingConfig.DATE_TIME_FORMAT));
        }
    }

    @Mapper
    interface Update {
        Update INSTANCE = Mappers.getMapper(Update.class);
        Lesson fromUpdateDtoToLesson(LessonUpdateDto dto);

        static LocalDateTime map(String value) {
            return LocalDateTime.parse(value, DateTimeFormatter.ofPattern(JsonDateMappingConfig.DATE_TIME_FORMAT));
        }
    }
}
