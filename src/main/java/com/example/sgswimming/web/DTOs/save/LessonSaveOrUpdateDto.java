package com.example.sgswimming.web.DTOs.save;

import com.example.sgswimming.web.config.JsonDateMappingConfig;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class LessonSaveOrUpdateDto {
    private Long id; //todo save dto should not have an id, update should

    @NotBlank
    @Length(max = 1000)
    private String description;

    @Pattern(regexp = JsonDateMappingConfig.DATE_TIME_FORMAT_REGEX)
    private String localDateTime;
}
