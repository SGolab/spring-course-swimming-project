package com.example.sgswimming.DTOs;

import com.example.sgswimming.config.JsonDateMappingConfig;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LessonSkinnyDto {
    private Long id;

    @NotBlank
    @Length(max = 1000)
    private String description;

    @Pattern(regexp = JsonDateMappingConfig.DATE_TIME_FORMAT_REGEX)
    private String localDateTime;

    private Long instructorId;

    @Singular
    private List<Long> swimmerIds;
}
