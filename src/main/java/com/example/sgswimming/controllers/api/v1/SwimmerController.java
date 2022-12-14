package com.example.sgswimming.controllers.api.v1;

import com.example.sgswimming.DTOs.SwimmerFatDto;
import com.example.sgswimming.DTOs.SwimmerSkinnyDto;
import com.example.sgswimming.services.SwimmerService;
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
@RequestMapping(SwimmerController.URL)
@RequiredArgsConstructor
public class SwimmerController {

    public static final String URL = "/api/v1/swimmers/";

    private final SwimmerService swimmerService;

    @GetMapping("/")
    public List<SwimmerFatDto> getAllSwimmers() {
        return swimmerService.findAll();
    }

    @GetMapping("/{id}")
    public SwimmerFatDto getInstructorById(@PathVariable Long id) {
        return swimmerService.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/")
    public SwimmerFatDto saveNewSwimmer(@Valid @RequestBody SwimmerSkinnyDto swimmerDTO) {
        return swimmerService.saveOrUpdate(swimmerDTO);
    }

    @PutMapping("/{id}")
    public SwimmerFatDto processUpdateSwimmer(@PathVariable Long id, @Valid @RequestBody SwimmerSkinnyDto swimmerDTO) {
        swimmerDTO.setId(id);
        return swimmerService.saveOrUpdate(swimmerDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteSwimmerById(@PathVariable Long id) {
        swimmerService.deleteById(id);
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