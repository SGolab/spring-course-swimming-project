package com.example.sgswimming.web.controllers.ui;

import com.example.sgswimming.web.DTOs.InstructorFatDto;
import com.example.sgswimming.web.DTOs.InstructorSkinnyDto;
import com.example.sgswimming.services.InstructorService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Profile("ui")
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
        model.addAttribute("instructor", InstructorFatDto.builder().build());
        return "/instructors/form";
    }

    @PostMapping(value = "/new")
    public String processSaveInstructor(@ModelAttribute InstructorSkinnyDto instructorDTO) {
        instructorService.saveOrUpdate(instructorDTO);
        return "redirect:/instructors/";
    }

    @GetMapping("/{id}/update")
    public String getInstructorUpdateForm(@PathVariable Long id, Model model) {
        InstructorFatDto dto = instructorService.findById(id);
        model.addAttribute("instructor", dto);
        return "/instructors/form";
    }

    @PostMapping("/{id}/update") //TODO wrong mapping but makes html work.
    public String processUpdateInstructor(@PathVariable Long id, @ModelAttribute InstructorSkinnyDto instructorDTO) {
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