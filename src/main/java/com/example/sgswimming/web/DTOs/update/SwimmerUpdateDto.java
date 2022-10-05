package com.example.sgswimming.web.DTOs.update;

import com.example.sgswimming.web.DTOs.validation_annotations.AdvanceLevelRestriction;
import com.example.sgswimming.web.config.JsonDateMappingConfig;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.util.HashSet;
import java.util.Set;

@Data
public class SwimmerUpdateDto {

    @NotNull
    private Long id;

    @NotBlank
    @Length(min = 3, max = 20)
    private String firstName;

    @NotBlank
    @Length(min = 3, max = 20)
    private String lastName;

    @AdvanceLevelRestriction
    private Integer advanceLevel;

    @Pattern(regexp = JsonDateMappingConfig.DATE_FORMAT_REGEX)
    private String birthDate;

    private Set<Long> lessons = new HashSet<>();
}
