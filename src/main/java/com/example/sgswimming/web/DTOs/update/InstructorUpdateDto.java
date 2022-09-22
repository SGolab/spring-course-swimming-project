package com.example.sgswimming.web.DTOs.update;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Data
public class InstructorUpdateDto {

    @NotNull
    private Long id;

    @NotBlank
    @Length(min = 3, max = 20)
    private String firstName;

    @NotBlank
    @Length(min = 3, max = 20)
    private String lastName;

    Set<Long> lessons = new HashSet<>();
}
