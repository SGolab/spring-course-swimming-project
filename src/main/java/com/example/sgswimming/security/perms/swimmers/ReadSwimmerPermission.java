package com.example.sgswimming.security.perms.swimmers;

import org.springframework.security.access.prepost.PreAuthorize;

@PreAuthorize("hasAuthority('swimmer.read')" +
        "OR hasAuthority('employee.swimmer.read')" +
        "OR hasAuthority('customer.swimmer.read')")
public @interface ReadSwimmerPermission {
}
