package com.example.sgswimming.web.mappers;


import com.example.sgswimming.model.Instructor;
import com.example.sgswimming.web.DTOs.read.InstructorReadDto;
import com.example.sgswimming.web.DTOs.save.InstructorSaveDto;
import com.example.sgswimming.web.DTOs.update.InstructorUpdateDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

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

    InstructorMapper.Read readMapper = Mappers.getMapper(InstructorMapper.Read.class);
    InstructorMapper.Save saveMapper = Mappers.getMapper(InstructorMapper.Save.class);
    InstructorMapper.Update updateMapper = Mappers.getMapper(InstructorMapper.Update.class);

    public InstructorReadDto toReadDto(Instructor instructor) {
        return readMapper.toReadDto(instructor);
    }

    public Instructor fromSaveDtoToInstructor(InstructorSaveDto dto) {
        return saveMapper.fromSaveDtoToInstructor(dto);
    }

    public Instructor fromUpdateDtoToInstructor(InstructorUpdateDto dto) {
        return updateMapper.fromUpdateDtoToInstructor(dto);
    }


    @Mapper(uses = LessonMapper.Read.class)
    interface Read {
        InstructorMapper.Read INSTANCE = Mappers.getMapper(InstructorMapper.Read.class);
        InstructorReadDto toReadDto(Instructor instructor);
    }

    @Mapper
    interface Save {
        InstructorMapper.Save INSTANCE = Mappers.getMapper(InstructorMapper.Save.class);
        Instructor fromSaveDtoToInstructor(InstructorSaveDto dto);
    }

    @Mapper(uses = LessonMapper.SaveOrUpdate.class)
    interface Update {
        InstructorMapper.Update INSTANCE = Mappers.getMapper(InstructorMapper.Update.class);
        Instructor fromUpdateDtoToInstructor(InstructorUpdateDto dto);
    }
}
