package com.example.sgswimming.web.DTOs;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LessonFatDto {

    private Long id;
    private String description;
    private LocalDateTime localDateTime;
    private InstructorSkinnyDto instructor;

    @Singular
    private List<SwimmerSkinnyDto> swimmers = new ArrayList<>();

}
