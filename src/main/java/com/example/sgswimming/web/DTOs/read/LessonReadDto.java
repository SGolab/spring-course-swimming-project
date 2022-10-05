package com.example.sgswimming.web.DTOs.read;

import lombok.Data;

import java.util.Set;

@Data
public class LessonReadDto {

    private Long id;
    private String description;
    private String localDateTime;

    private Instructor instructor;
    private Set<Swimmer> swimmers;
    private Integer advanceLevel;

    @Data
    public static class Instructor {
        private Long id;
        private String firstName;
        private String lastName;
    }

    @Data
    public static class Swimmer {
        private Long id;
        private String firstName;
        private String lastName;
    }
}
