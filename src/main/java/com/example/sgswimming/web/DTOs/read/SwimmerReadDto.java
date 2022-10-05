package com.example.sgswimming.web.DTOs.read;

import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public class SwimmerReadDto {

    private Long id;
    private String firstName;
    private String lastName;
    private Integer advanceLevel;
    private String birthDate;

    private Set<LessonReadDto> lessons;
}
