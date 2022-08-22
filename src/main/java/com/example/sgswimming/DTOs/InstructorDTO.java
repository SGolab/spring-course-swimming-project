package com.example.sgswimming.DTOs;

import com.example.sgswimming.model.Lesson;
import lombok.*;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class InstructorDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private List<Lesson> lessons;
}
