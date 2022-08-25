package com.example.sgswimming.mappers;

import com.example.sgswimming.DTOs.SwimmerDTO;
import com.example.sgswimming.model.Swimmer;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SwimmerMapper {
    SwimmerMapper INSTANCE = Mappers.getMapper(SwimmerMapper.class);

    SwimmerDTO toDto(Swimmer swimmer, @Context CycleAvoidingMappingContext context);
    Swimmer toSwimmer(SwimmerDTO dto, @Context CycleAvoidingMappingContext context);

    @DoIgnore
    default SwimmerDTO toDto(Swimmer swimmer) {
        return toDto(swimmer, new CycleAvoidingMappingContext());
    }

    @DoIgnore
    default Swimmer toSwimmer(SwimmerDTO dto) {
        return toSwimmer(dto, new CycleAvoidingMappingContext());
    }
}
