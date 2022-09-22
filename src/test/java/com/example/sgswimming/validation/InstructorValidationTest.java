package com.example.sgswimming.validation;

import com.example.sgswimming.web.DTOs.save.InstructorSaveDto;
import com.example.sgswimming.web.DTOs.update.InstructorUpdateDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InstructorValidationTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void validateInstructorSaveDtoValid() {
        InstructorSaveDto instructorSaveDto = new InstructorSaveDto();
        instructorSaveDto.setFirstName("first name");
        instructorSaveDto.setLastName("last name");

        Set<Path> violations = validator.validate(instructorSaveDto)
                .stream()
                .map(ConstraintViolation::getPropertyPath)
                .collect(Collectors.toSet());

        assertEquals(0, violations.size());
    }

    @Test
    void validateInstructorSaveDtoInvalid() {
        InstructorSaveDto instructorSaveDto = new InstructorSaveDto();
        instructorSaveDto.setFirstName("");
        instructorSaveDto.setLastName("");

        Set<Path> violations = validator.validate(instructorSaveDto)
                .stream()
                .map(ConstraintViolation::getPropertyPath)
                .collect(Collectors.toSet());

        assertEquals(2, violations.size());
    }

    @Test
    void validateInstructorUpdateDtoValid() {
        InstructorUpdateDto instructorUpdateDto = new InstructorUpdateDto();
        instructorUpdateDto.setId(1L);
        instructorUpdateDto.setFirstName("first name");
        instructorUpdateDto.setLastName("last name");
        instructorUpdateDto.setLessons(Set.of(1L, 2L, 3L));

        Set<Path> violations = validator.validate(instructorUpdateDto)
                .stream()
                .map(ConstraintViolation::getPropertyPath)
                .collect(Collectors.toSet());

        assertEquals(0, violations.size());
    }

    @Test
    void validateInstructorUpdateDtoInvalid() {
        InstructorUpdateDto instructorUpdateDto = new InstructorUpdateDto();
        instructorUpdateDto.setId(null);
        instructorUpdateDto.setFirstName("");
        instructorUpdateDto.setLastName("");
        instructorUpdateDto.setLessons(Set.of(1L, 2L, 3L));

        Set<Path> violations = validator.validate(instructorUpdateDto)
                .stream()
                .map(ConstraintViolation::getPropertyPath)
                .collect(Collectors.toSet());

        assertEquals(3, violations.size());
    }
}
