package com.example.sgswimming.mappers;


import com.example.sgswimming.DTOs.InstructorDTO;
import com.example.sgswimming.model.Instructor;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface InstructorMapper {
    InstructorMapper INSTANCE = Mappers.getMapper(InstructorMapper.class);
    InstructorDTO toDto(Instructor doctor);
    Instructor toInstructor(InstructorDTO dto);
}
