package com.example.sgswimming.web.DTOs.save;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class InstructorSaveDto {
    @NotBlank
    @Length(min = 3, max = 20)
    private String firstName;

    @NotBlank
    @Length(min = 3, max = 20)
    private String lastName;
}
