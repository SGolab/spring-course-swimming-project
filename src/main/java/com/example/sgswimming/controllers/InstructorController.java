package com.example.sgswimming.controllers;

import com.example.sgswimming.DTOs.InstructorDTO;
import com.example.sgswimming.services.InstructorService;
import com.example.sgswimming.services.SwimmerService;
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

    @GetMapping("/new")
    public String getInstructorCreationForm(Model model) {
        model.addAttribute("instructor", new InstructorDTO());
        return "/instructors/form";
    }

}