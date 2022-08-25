package com.example.sgswimming.mappers;


import com.example.sgswimming.DTOs.InstructorDTO;
import com.example.sgswimming.model.Instructor;
import org.mapstruct.Context;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface InstructorMapper {
    InstructorMapper INSTANCE = Mappers.getMapper(InstructorMapper.class);

    InstructorDTO toDto(Instructor instructor, @Context CycleAvoidingMappingContext context);
    Instructor toInstructor(InstructorDTO dto, @Context CycleAvoidingMappingContext context);

    @DoIgnore
    default InstructorDTO toDto(Instructor instructor) {
        return toDto(instructor, new CycleAvoidingMappingContext());
    }

    @DoIgnore
    default Instructor toInstructor(InstructorDTO dto) {
        return toInstructor(dto, new CycleAvoidingMappingContext());
    }

}
