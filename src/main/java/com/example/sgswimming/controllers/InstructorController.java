package com.example.sgswimming.controllers;

import com.example.sgswimming.DTOs.InstructorDTO;
import com.example.sgswimming.services.InstructorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping(value = "/new")
    public String processSaveInstructor(@ModelAttribute InstructorDTO instructorDTO) {
        instructorService.saveOrUpdate(instructorDTO);
        return "redirect:/instructors/";
    }

    @GetMapping("/{id}/update")
    public String getInstructorUpdateForm(@PathVariable Long id, Model model) {
        InstructorDTO dto = instructorService.findById(id);
        model.addAttribute("instructor", dto);
        return "/instructors/form";
    }

    @PostMapping("/{id}/update") //TODO wrong mapping but makes html work.
    public String processUpdateInstructor(@PathVariable Long id, @ModelAttribute InstructorDTO instructorDTO) {
        instructorDTO.setId(id);
        instructorService.saveOrUpdate(instructorDTO);
        return "redirect:/instructors/";
    }

    @GetMapping("/{id}/delete") //TODO wrong mapping but makes html work.
    public String deleteInstructorById(@PathVariable Long id) {
        instructorService.deleteById(id);
        return "redirect:/instructors/";
    }
}