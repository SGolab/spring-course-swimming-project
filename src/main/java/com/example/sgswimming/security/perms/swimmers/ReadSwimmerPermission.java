package com.example.sgswimming.security.perms.swimmers;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasAuthority('swimmer.read')" +
        "OR hasAuthority('employee.swimmer.read')" +
        "OR hasAuthority('customer.swimmer.read')")
public @interface ReadSwimmerPermission {
}
