package com.example.sgswimming.DTOs;

import com.example.sgswimming.model.Lesson;
import lombok.Data;

import java.util.List;

@Data
public class InstructorDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private List<Lesson> lessons;
}
