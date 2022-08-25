package com.example.sgswimming.DTOs;

import com.example.sgswimming.model.Lesson;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data

public class SwimmerDTO {
    private Long id;
    private String firstName;
    private String lastName;

    private List<LessonDTO.Skinny> lessons;

    @Data
    public static class Skinny {
        private Long id;
        private String firstName;
        private String lastName;

        private List<Long> lessonIds;
    }
}
