package com.example.sgswimming.mappers;

import com.example.sgswimming.DTOs.SwimmerDTO;
import com.example.sgswimming.model.Swimmer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SwimmerMapper {
    SwimmerMapper INSTANCE = Mappers.getMapper(SwimmerMapper.class);
    SwimmerDTO toDto(Swimmer swimmer);
    Swimmer toSwimmer(SwimmerDTO dto);
}
