package com.example.sgswimming.DTOs;

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

    @Singular
    private List<LessonDTO.Skinny> lessons;

    @Data
    public static class Skinny {
        private Long id;
        private String firstName;
        private String lastName;

        @Singular
        private List<Long> lessonIds;
    }
}
