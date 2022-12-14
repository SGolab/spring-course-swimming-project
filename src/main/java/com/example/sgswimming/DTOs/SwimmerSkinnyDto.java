package com.example.sgswimming.DTOs;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SwimmerSkinnyDto {

    private Long id;

    @NotBlank
    @Length(min = 3, max = 20)
    private String firstName;

    @NotBlank
    @Length(min = 3, max = 20)
    private String lastName;

    @Singular
    private List<Long> lessonIds;
}
