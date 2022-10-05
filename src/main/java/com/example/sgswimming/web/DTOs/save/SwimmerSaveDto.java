package com.example.sgswimming.web.DTOs.save;

import com.example.sgswimming.web.DTOs.validation_annotations.AdvanceLevelRestriction;
import com.example.sgswimming.web.config.JsonDateMappingConfig;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class SwimmerSaveDto {

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
}
