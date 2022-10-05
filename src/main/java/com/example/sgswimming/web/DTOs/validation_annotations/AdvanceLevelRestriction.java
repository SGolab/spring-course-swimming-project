package com.example.sgswimming.web.DTOs.validation_annotations;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Min(0)
@Max(5)
public @interface AdvanceLevelRestriction {
}
