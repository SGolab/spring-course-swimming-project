package com.example.sgswimming.controllers;

import com.example.sgswimming.services.SwimmerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/swimmers")
public class SwimmerController {

    private final SwimmerService swimmerService;

    @GetMapping("/")
    public String getAllSwimmers(Model model) {
        model.addAttribute("swimmers", swimmerService.findAll());
        return "swimmers/list";
    }

    @GetMapping("/{id}")
    public String getInstructorById(Model model, @PathVariable Long id) {
        model.addAttribute("swimmer", swimmerService.findById(id));
        return "swimmers/show";
    }

}
