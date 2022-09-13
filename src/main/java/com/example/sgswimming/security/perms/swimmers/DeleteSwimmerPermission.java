package com.example.sgswimming.security.perms.swimmers;

import org.springframework.security.access.prepost.PreAuthorize;

@PreAuthorize("hasAuthority('swimmer.delete')")
public @interface DeleteSwimmerPermission {
}
