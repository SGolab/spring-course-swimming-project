package com.example.swimmingproject.controllers;

import com.example.swimmingproject.services.InstructorService;
import com.example.swimmingproject.services.SwimmerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/instructors")
public class InstructorController {

    private final InstructorService instructorService;

    @GetMapping("/")
    public String getAllInstructors(Model model) {
        model.addAttribute("instructors", instructorService.findAll());
        return "instructors/list";
    }

    @GetMapping("/{id}")
    public String getInstructorById(Model model, @PathVariable Long id) {
        model.addAttribute("instructor", instructorService.findById(id));
        return "instructors/show";
    }

}