package com.example.sgswimming.validation;

import com.example.sgswimming.DTOs.InstructorSkinnyDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InstructorValidationTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void validateInstructorSkinnyDtoValid() {
        InstructorSkinnyDto INSTRUCTOR_SKINNY_DTO = InstructorSkinnyDto.builder()
                .firstName("John")
                .lastName("Kowalski")
                .lessonIds(List.of(1L, 2L, 3L))
                .build();

        Set<Path> violations = validator.validate(INSTRUCTOR_SKINNY_DTO)
                .stream()
                .map(ConstraintViolation::getPropertyPath)
                .collect(Collectors.toSet());

        assertEquals(violations.size(), 0);
    }

    @Test
    void validateInstructorSkinnyDtoInvalid() {
        InstructorSkinnyDto INSTRUCTOR_SKINNY_DTO = InstructorSkinnyDto.builder()
                .firstName("")
                .lastName("")
                .lessonIds(List.of(1L, 2L, 3L))
                .build();

        Set<Path> violations = validator.validate(INSTRUCTOR_SKINNY_DTO)
                .stream()
                .map(ConstraintViolation::getPropertyPath)
                .collect(Collectors.toSet());

        int EXPECTED_VIOLATIONS_AMOUNT = 2;
        assertEquals(violations.size(), EXPECTED_VIOLATIONS_AMOUNT);
    }
}
