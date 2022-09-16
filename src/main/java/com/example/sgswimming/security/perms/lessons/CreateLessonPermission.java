package com.example.sgswimming.security.perms.lessons;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasAuthority('lesson.create')")
public @interface CreateLessonPermission {
}
