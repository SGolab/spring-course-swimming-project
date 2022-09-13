package com.example.sgswimming.security.perms.lessons;

import org.springframework.security.access.prepost.PreAuthorize;

@PreAuthorize("hasAuthority('lesson.read')" +
        "OR hasAuthority('employee.lesson.read')" +
        "OR hasAuthority('customer.lesson.read')")
public @interface ReadLessonPermission {
}
