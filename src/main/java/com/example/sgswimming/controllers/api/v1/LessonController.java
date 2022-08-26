package com.example.sgswimming.controllers.api.v1;

import com.example.sgswimming.DTOs.LessonDTO;
import com.example.sgswimming.DTOs.LessonDTO;
import com.example.sgswimming.services.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Profile("api")
@RestController
@RequestMapping("api/v1/lessons")
@RequiredArgsConstructor
public class LessonController {

    private final LessonService lessonService;

    @GetMapping("/")
    public List<LessonDTO> getAllLessons() {
        return lessonService.findAll();
    }

    @GetMapping("/{id}")
    public LessonDTO getLessonById(@PathVariable Long id) {
        return lessonService.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/")
    public LessonDTO saveNewLesson(@RequestBody LessonDTO lessonDTO) {
        return lessonService.saveOrUpdate(lessonDTO);
    }

    @PutMapping("/{id}")
    public LessonDTO processUpdateLesson(@PathVariable Long id, @RequestBody LessonDTO lessonDTO) {
        lessonDTO.setId(id);
        return lessonService.saveOrUpdate(lessonDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteLessonById(@PathVariable Long id) {
        lessonService.deleteById(id);
    }
}
