package com.example.sgswimming.controllers.api.v1;

import com.example.sgswimming.DTOs.LessonDTO;
import com.example.sgswimming.services.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/lessons")
public class LessonController {

    private final LessonService lessonService;

    @GetMapping("/")
    public List<LessonDTO> getAllLessons() {
        return lessonService.findAll();
    }

    @GetMapping("/{id}")
    public LessonDTO getInstructorById(@PathVariable Long id) {
        return lessonService.findById(id);
    }
}
