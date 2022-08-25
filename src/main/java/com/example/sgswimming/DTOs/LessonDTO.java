package com.example.sgswimming.DTOs;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data

public class LessonDTO {

    private Long id;
    private String description;
    private LocalDateTime localDateTime;

    private InstructorDTO.Skinny instructor;
    private List<SwimmerDTO.Skinny> swimmers = new ArrayList<>();

    @Data
    public static class Skinny {
        private Long id;
        private String description;

        private Long instructorId;
        private List<Long> swimmerIds;
    }

}
