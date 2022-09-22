package com.example.sgswimming.web.mappers;

import com.example.sgswimming.model.Swimmer;
import com.example.sgswimming.web.DTOs.read.SwimmerReadDto;
import com.example.sgswimming.web.DTOs.save.SwimmerSaveDto;
import com.example.sgswimming.web.DTOs.update.SwimmerUpdateDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

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
    }

    @Mapper(uses = LessonMapper.SaveOrUpdate.class)
    interface Update {
        SwimmerMapper.Update INSTANCE = Mappers.getMapper(SwimmerMapper.Update.class);
        Swimmer fromUpdateDtoToSwimmer(SwimmerUpdateDto dto);
    }
}
