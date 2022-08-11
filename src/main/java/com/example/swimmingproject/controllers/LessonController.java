package com.example.swimmingproject.controllers;

import com.example.swimmingproject.services.InstructorService;
import com.example.swimmingproject.services.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/lessons")
public class LessonController {

    private final LessonService lessonService;

    @GetMapping("/")
    public String getAllLessons(Model model) {
        model.addAttribute("lessons", lessonService.findAll());
        return "lessons/list";
    }

    @GetMapping("/{id}")
    public String getInstructorById(Model model, @PathVariable Long id) {
        model.addAttribute("lesson", lessonService.findById(id));
        return "lessons/show";
    }
}
