package com.example.sgswimming.validation;

import com.example.sgswimming.web.DTOs.LessonSkinnyDto;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LessonValidationTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void validateLessonSkinnyDtoValid() {
        LessonSkinnyDto LESSON_SKINNY_DTO = LessonSkinnyDto.builder()
                .description("Desc")
                .localDateTime("01:01 01.01.2000")
                .swimmerIds(List.of(1L, 2L, 3L))
                .build();

        Set<Path> violations = validator.validate(LESSON_SKINNY_DTO)
                .stream()
                .map(ConstraintViolation::getPropertyPath)
                .collect(Collectors.toSet());

        assertEquals(violations.size(), 0);
    }

    @Test
    void validateLessonSkinnyDtoInvalid() {
        LessonSkinnyDto LESSON_SKINNY_DTO = LessonSkinnyDto.builder()
                .description("")
                .localDateTime("01:01 01.01.200")
                .swimmerIds(List.of(1L, 2L, 3L))
                .build();

        Set<Path> violations = validator.validate(LESSON_SKINNY_DTO)
                .stream()
                .map(ConstraintViolation::getPropertyPath)
                .collect(Collectors.toSet());

        int EXPECTED_VIOLATIONS_AMOUNT = 2;
        assertEquals(EXPECTED_VIOLATIONS_AMOUNT, violations.size());
    }
}
