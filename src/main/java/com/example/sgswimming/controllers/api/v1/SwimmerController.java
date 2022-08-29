package com.example.sgswimming.controllers.api.v1;

import com.example.sgswimming.DTOs.SwimmerDTO;
import com.example.sgswimming.DTOs.SwimmerDTO;
import com.example.sgswimming.services.SwimmerService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Profile("api")
@RestController
@RequestMapping(SwimmerController.URL)
@RequiredArgsConstructor
public class SwimmerController {

    public static final String URL = "/api/v1/swimmers/";

    private final SwimmerService swimmerService;

    @GetMapping("/")
    public List<SwimmerDTO> getAllSwimmers() {
        return swimmerService.findAll();
    }

    @GetMapping("/{id}")
    public SwimmerDTO getInstructorById(@PathVariable Long id) {
        return swimmerService.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/")
    public SwimmerDTO saveNewSwimmer(@RequestBody SwimmerDTO swimmerDTO) {
        return swimmerService.saveOrUpdate(swimmerDTO);
    }

    @PutMapping("/{id}")
    public SwimmerDTO processUpdateSwimmer(@PathVariable Long id, @RequestBody SwimmerDTO swimmerDTO) {
        swimmerDTO.setId(id);
        return swimmerService.saveOrUpdate(swimmerDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteSwimmerById(@PathVariable Long id) {
        swimmerService.deleteById(id);
    }
}