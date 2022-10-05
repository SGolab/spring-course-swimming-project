package com.example.sgswimming.web.mappers;

import com.example.sgswimming.model.Swimmer;
import com.example.sgswimming.web.DTOs.read.SwimmerReadDto;
import com.example.sgswimming.web.DTOs.save.SwimmerSaveDto;
import com.example.sgswimming.web.DTOs.update.SwimmerUpdateDto;
import com.example.sgswimming.web.config.JsonDateMappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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

    SwimmerMapper.Read readMapper = Mappers.getMapper(SwimmerMapper.Read.class);
    SwimmerMapper.Save saveMapper = Mappers.getMapper(SwimmerMapper.Save.class);
    SwimmerMapper.Update updateMapper = Mappers.getMapper(SwimmerMapper.Update.class);

    public SwimmerReadDto toReadDto(Swimmer swimmer) {
        return readMapper.toReadDto(swimmer);
    }

    public Swimmer fromSaveDtoToSwimmer(SwimmerSaveDto dto) {
        return saveMapper.fromSaveDtoToSwimmer(dto);
    }

    public Swimmer fromUpdateDtoToSwimmer(SwimmerUpdateDto dto) {
        return updateMapper.fromUpdateDtoToSwimmer(dto);
    }

    @Mapper(uses = LessonMapper.Read.class)
    interface Read {
        SwimmerMapper.Read INSTANCE = Mappers.getMapper(SwimmerMapper.Read.class);
        SwimmerReadDto toReadDto(Swimmer swimmer);
    }

    @Mapper
    interface Save {
        SwimmerMapper.Save INSTANCE = Mappers.getMapper(SwimmerMapper.Save.class);
        Swimmer fromSaveDtoToSwimmer(SwimmerSaveDto dto);

        static LocalDate map(String value) {
            if (value == null) return null;
            return LocalDate.parse(value, DateTimeFormatter.ofPattern(JsonDateMappingConfig.DATE_FORMAT));
        }
    }

    @Mapper(uses = LessonMapper.Update.class)
    interface Update {
        SwimmerMapper.Update INSTANCE = Mappers.getMapper(SwimmerMapper.Update.class);
        @Mapping(target = "lessons", ignore = true)
        Swimmer fromUpdateDtoToSwimmer(SwimmerUpdateDto dto);

        static LocalDate map(String value) {
            if (value == null) return null;
            return LocalDate.parse(value, DateTimeFormatter.ofPattern(JsonDateMappingConfig.DATE_FORMAT));
        }
    }
}
