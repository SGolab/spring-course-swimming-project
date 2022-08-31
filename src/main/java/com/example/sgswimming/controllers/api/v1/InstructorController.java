package com.example.sgswimming.controllers.api.v1;

import com.example.sgswimming.DTOs.InstructorDTO;
import com.example.sgswimming.services.InstructorService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public InstructorDTO saveNewInstructor(@Valid @RequestBody InstructorDTO instructorDTO) {
        return instructorService.saveOrUpdate(instructorDTO);
    }

    @PutMapping("/{id}")
    public InstructorDTO updateInstructor(@PathVariable Long id, @Valid @RequestBody InstructorDTO instructorDTO) {
        instructorDTO.setId(id);
        return instructorService.saveOrUpdate(instructorDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteInstructorById(@PathVariable Long id) {
        instructorService.deleteById(id);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return errors;
    }
}