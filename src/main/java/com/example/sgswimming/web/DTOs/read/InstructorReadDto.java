package com.example.sgswimming.web.DTOs.read;

import lombok.Data;

import java.util.Set;

@Data
public class InstructorReadDto {

    private Long id;
    private String firstName;
    private String lastName;

    private Set<LessonReadDto> lessons;
}
