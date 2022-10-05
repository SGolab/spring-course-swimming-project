package com.example.sgswimming.security.perms.lessons;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasAuthority('lesson.read')" +
        "OR hasAuthority('employee.lesson.read')")
public @interface ReadLessonWithoutInstructorPermission {
}
