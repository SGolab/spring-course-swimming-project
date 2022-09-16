package com.example.sgswimming.web.controllers.api.v1;

import com.example.sgswimming.model.exceptions.NotFoundException;
import com.example.sgswimming.security.perms.lessons.CreateLessonPermission;
import com.example.sgswimming.security.perms.lessons.DeleteLessonPermission;
import com.example.sgswimming.security.perms.lessons.ReadLessonPermission;
import com.example.sgswimming.security.perms.lessons.UpdateLessonPermission;
import com.example.sgswimming.services.LessonService;
import com.example.sgswimming.web.DTOs.LessonFatDto;
import com.example.sgswimming.web.DTOs.LessonSkinnyDto;
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
@RequestMapping(LessonController.URL)
@RequiredArgsConstructor
public class LessonController {

    public static final String URL = "/api/v1/lessons/";

    private final LessonService lessonService;

    @ReadLessonPermission
    @GetMapping("/")
    public List<LessonFatDto> getAllLessons() {
        return lessonService.findAll();
    }

    @ReadLessonPermission
    @GetMapping("/{id}")
    public LessonFatDto getLessonById(@PathVariable Long id) {
        return lessonService.findById(id);
    }

    @CreateLessonPermission
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/")
    public LessonFatDto saveNewLesson(@Valid @RequestBody LessonSkinnyDto lessonDTO) {
        return lessonService.saveOrUpdate(lessonDTO);
    }

    @UpdateLessonPermission
    @PutMapping("/{id}")
    public LessonFatDto processUpdateLesson(@PathVariable Long id, @Valid @RequestBody LessonSkinnyDto lessonDTO) {
        lessonDTO.setId(id);
        return lessonService.saveOrUpdate(lessonDTO);
    }

    @DeleteLessonPermission
    @DeleteMapping("/{id}")
    public void deleteLessonById(@PathVariable Long id) {
        lessonService.deleteById(id);
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
