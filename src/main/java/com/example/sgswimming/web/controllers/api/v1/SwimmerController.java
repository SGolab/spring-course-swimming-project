package com.example.sgswimming.web.controllers.api.v1;

import com.example.sgswimming.model.exceptions.NotFoundException;
import com.example.sgswimming.security.model.User;
import com.example.sgswimming.security.model.exceptions.InvalidUserException;
import com.example.sgswimming.security.perms.swimmers.CreateSwimmerPermission;
import com.example.sgswimming.security.perms.swimmers.DeleteSwimmerPermission;
import com.example.sgswimming.security.perms.swimmers.ReadSwimmerPermission;
import com.example.sgswimming.security.perms.swimmers.UpdateSwimmerPermission;
import com.example.sgswimming.web.DTOs.SwimmerFatDto;
import com.example.sgswimming.web.DTOs.SwimmerSkinnyDto;
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

    @ReadSwimmerPermission
    @GetMapping("/")
    public List<SwimmerFatDto> getAllSwimmers() {
        return swimmerService.findAll();
    }

    @ReadSwimmerPermission
    @GetMapping("/{id}")
    public SwimmerFatDto getInstructorById(@PathVariable Long id) {
        return swimmerService.findById(id);
    }

    @CreateSwimmerPermission
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/")
    public SwimmerFatDto saveNewSwimmer(@Valid @RequestBody SwimmerSkinnyDto swimmerDTO) {
        return swimmerService.saveOrUpdate(swimmerDTO);
    }

    @UpdateSwimmerPermission
    @PutMapping("/{id}")
    public SwimmerFatDto processUpdateSwimmer(@PathVariable Long id, @Valid @RequestBody SwimmerSkinnyDto swimmerDTO) {
        swimmerDTO.setId(id);
        return swimmerService.saveOrUpdate(swimmerDTO);
    }

    @DeleteSwimmerPermission
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

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    protected Map<String, String> handle(NotFoundException ex) {
        return Map.of(ex.getAClass().getSimpleName(), String.valueOf(ex.getId()));
    }
}