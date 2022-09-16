package com.example.sgswimming.web.controllers.api.v1;

import com.example.sgswimming.model.exceptions.NotFoundException;
import com.example.sgswimming.security.model.User;
import com.example.sgswimming.security.perms.instructors.CreateInstructorPermission;
import com.example.sgswimming.security.perms.instructors.ReadInstructorPermission;
import com.example.sgswimming.security.perms.instructors.RemoveInstructorPermission;
import com.example.sgswimming.security.perms.instructors.UpdateInstructorPermission;
import com.example.sgswimming.web.DTOs.InstructorFatDto;
import com.example.sgswimming.web.DTOs.InstructorSkinnyDto;
import com.example.sgswimming.services.InstructorService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @ReadInstructorPermission
    @GetMapping("/")
    public List<InstructorFatDto> getAllInstructors() {
        return instructorService.findAll();
    }

    @ReadInstructorPermission
    @GetMapping("/{id}")
    public InstructorFatDto getInstructorById(@PathVariable Long id) {
        return instructorService.findById(id);
    }

    @CreateInstructorPermission
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/")
    public InstructorFatDto saveNewInstructor(@Valid @RequestBody InstructorSkinnyDto instructorDTO) {
        return instructorService.saveOrUpdate(instructorDTO);
    }

    @UpdateInstructorPermission
    @PutMapping("/{id}")
    public InstructorFatDto updateInstructor(@PathVariable Long id, @Valid @RequestBody InstructorSkinnyDto instructorDTO) {
        instructorDTO.setId(id);
        return instructorService.saveOrUpdate(instructorDTO);
    }

    @RemoveInstructorPermission
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

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    protected Map<String, String> handle(NotFoundException ex) {
        return Map.of(ex.getAClass().getSimpleName(), String.valueOf(ex.getId()));
    }
}