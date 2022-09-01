package com.example.sgswimming.controllers.api.v1;

import com.example.sgswimming.DTOs.LessonFatDto;
import com.example.sgswimming.DTOs.LessonSkinnyDto;
import com.example.sgswimming.services.LessonService;
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

    @GetMapping("/")
    public List<LessonFatDto> getAllLessons() {
        return lessonService.findAll();
    }

    @GetMapping("/{id}")
    public LessonFatDto getLessonById(@PathVariable Long id) {
        return lessonService.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/")
    public LessonFatDto saveNewLesson(@Valid @RequestBody LessonSkinnyDto lessonDTO) {
        return lessonService.saveOrUpdate(lessonDTO);
    }

    @PutMapping("/{id}")
    public LessonFatDto processUpdateLesson(@PathVariable Long id, @Valid @RequestBody LessonSkinnyDto lessonDTO) {
        lessonDTO.setId(id);
        return lessonService.saveOrUpdate(lessonDTO);
    }

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
}
