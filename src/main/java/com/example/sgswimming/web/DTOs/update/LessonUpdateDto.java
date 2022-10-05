package com.example.sgswimming.web.DTOs.update;

import com.example.sgswimming.web.DTOs.validation_annotations.AdvanceLevelRestriction;
import com.example.sgswimming.web.config.JsonDateMappingConfig;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.HashSet;
import java.util.Set;

@Data
public class LessonUpdateDto {

    @NotNull
    private Long id;

    @NotBlank
    @Length(max = 1000)
    private String description;

    @Pattern(regexp = JsonDateMappingConfig.DATE_TIME_FORMAT_REGEX)
    private String localDateTime;

    @AdvanceLevelRestriction
    private Integer advanceLevel;
}
