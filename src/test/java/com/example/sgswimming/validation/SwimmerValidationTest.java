package com.example.sgswimming.validation;

import com.example.sgswimming.DTOs.SwimmerSkinnyDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SwimmerValidationTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void validateSwimmerSkinnyDtoValid() {
        SwimmerSkinnyDto SWIMMER_SKINNY_DTO = SwimmerSkinnyDto.builder()
                .firstName("John")
                .lastName("Kowalski")
                .lessonIds(List.of(1L, 2L, 3L))
                .build();

        Set<Path> violations = validator.validate(SWIMMER_SKINNY_DTO)
                .stream()
                .map(ConstraintViolation::getPropertyPath)
                .collect(Collectors.toSet());

        assertEquals(violations.size(), 0);
    }

    @Test
    void validateSwimmerSkinnyDtoInvalid() {
        SwimmerSkinnyDto SWIMMER_SKINNY_DTO = SwimmerSkinnyDto.builder()
                .firstName("")
                .lastName("")
                .lessonIds(List.of(1L, 2L, 3L))
                .build();

        Set<Path> violations = validator.validate(SWIMMER_SKINNY_DTO)
                .stream()
                .map(ConstraintViolation::getPropertyPath)
                .collect(Collectors.toSet());

        int EXPECTED_VIOLATIONS_AMOUNT = 2;
        assertEquals(violations.size(), EXPECTED_VIOLATIONS_AMOUNT);
    }
}
