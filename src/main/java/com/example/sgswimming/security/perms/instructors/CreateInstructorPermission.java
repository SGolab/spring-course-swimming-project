package com.example.sgswimming.security.perms.instructors;

import org.springframework.security.access.prepost.PreAuthorize;

@PreAuthorize("hasAuthority('instructor.create')")
public @interface CreateInstructorPermission {}
