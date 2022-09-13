package com.example.sgswimming.security.perms.lessons;

import org.springframework.security.access.prepost.PreAuthorize;

@PreAuthorize("hasAuthority('lesson.update')")
public @interface UpdateLessonPermission {
}
