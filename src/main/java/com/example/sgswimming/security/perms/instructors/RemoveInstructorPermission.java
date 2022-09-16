package com.example.sgswimming.security.perms.instructors;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasAuthority('instructor.remove')")
public @interface RemoveInstructorPermission {
}
