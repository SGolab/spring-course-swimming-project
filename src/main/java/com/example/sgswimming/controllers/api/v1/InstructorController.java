package com.example.sgswimming.controllers.api.v1;

import com.example.sgswimming.DTOs.InstructorDTO;
import com.example.sgswimming.services.InstructorService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Profile("api")
@RestController
@RequestMapping(InstructorController.URL)
@RequiredArgsConstructor
public class InstructorController {
    
    public static final String URL = "/api/v1/instructors/";

    private final InstructorService instructorService;

    @GetMapping("/")
    public List<InstructorDTO> getAllInstructors() {
        return instructorService.findAll();
    }

    @GetMapping("/{id}")
    public InstructorDTO getInstructorById(@PathVariable Long id) {
        return instructorService.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/")
    public InstructorDTO saveNewInstructor(@RequestBody InstructorDTO instructorDTO) {
        return instructorService.saveOrUpdate(instructorDTO);
    }

    @PutMapping("/{id}")
    public InstructorDTO processUpdateInstructor(@PathVariable Long id, @RequestBody InstructorDTO instructorDTO) {
        instructorDTO.setId(id);
        return instructorService.saveOrUpdate(instructorDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteInstructorById(@PathVariable Long id) {
        instructorService.deleteById(id);
    }
}