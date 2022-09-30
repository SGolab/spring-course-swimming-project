package com.example.sgswimming.web.controllers.api.v1;

import com.example.sgswimming.model.exceptions.NotFoundException;
import com.example.sgswimming.security.model.User;
import com.example.sgswimming.security.perms.lessons.CreateLessonPermission;
import com.example.sgswimming.security.perms.lessons.DeleteLessonPermission;
import com.example.sgswimming.security.perms.lessons.ReadLessonPermission;
import com.example.sgswimming.security.perms.lessons.UpdateLessonPermission;
import com.example.sgswimming.services.LessonService;
import com.example.sgswimming.web.DTOs.read.LessonReadDto;
import com.example.sgswimming.web.DTOs.save.LessonSaveDto;
import com.example.sgswimming.web.DTOs.update.LessonUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(LessonController.URL)
@RequiredArgsConstructor
public class LessonController {

    public static final String URL = "/api/v1/lessons/";

    private final LessonService lessonService;

    @ReadLessonPermission
    @GetMapping("/")
    public List<LessonReadDto> getAllLessons(@AuthenticationPrincipal User user) {
        if (user.getClientData() == null) {
            return lessonService.findAll();
        } else {
            return lessonService.findAll(user.getClientData());
        }
    }

    @ReadLessonPermission
    @GetMapping("/{id}")
    public LessonReadDto getLessonById(@AuthenticationPrincipal User user, @PathVariable Long id) {
        if (user.getClientData() == null) {
            return lessonService.findById(id);
        } else {
            return lessonService.findById(user.getClientData(), id);
        }
    }

    @CreateLessonPermission
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/")
    public LessonReadDto saveNewLesson(@Valid @RequestBody LessonSaveDto lessonDTO) {
        return lessonService.save(lessonDTO);
    }

    @UpdateLessonPermission
    @PutMapping("/{id}")
    public LessonReadDto processUpdateLesson(@PathVariable Long id, @Valid @RequestBody LessonUpdateDto lessonDTO) {
        lessonDTO.setId(id);
        return lessonService.update(lessonDTO);
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
