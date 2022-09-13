package com.example.sgswimming.security.perms.instructors;

import org.springframework.security.access.prepost.PreAuthorize;

@PreAuthorize("hasAuthority('instructor.read')" +
        "OR hasAuthority('employee.instructor.read')" +
        "OR hasAuthority('customer.instructor.read')")
public @interface ReadInstructorPermission {
}
