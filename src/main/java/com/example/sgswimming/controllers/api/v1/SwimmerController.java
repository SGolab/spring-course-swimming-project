package com.example.sgswimming.controllers.api.v1;

import com.example.sgswimming.DTOs.SwimmerDTO;
import com.example.sgswimming.services.SwimmerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api/v1/swimmers")
public class SwimmerController {

    private final SwimmerService swimmerService;

    @GetMapping("/")
    public List<SwimmerDTO> getAllSwimmers() {
        return swimmerService.findAll();
    }

    @GetMapping("/{id}")
    public SwimmerDTO getInstructorById(@PathVariable Long id) {
        return swimmerService.findById(id);
    }
}