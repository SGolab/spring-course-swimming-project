package com.example.sgswimming.services;

import com.example.sgswimming.model.Instructor;
import com.example.sgswimming.model.Lesson;
import com.example.sgswimming.model.Swimmer;
import com.example.sgswimming.model.exceptions.NotFoundException;
import com.example.sgswimming.repositories.InstructorRepository;
import com.example.sgswimming.repositories.LessonRepository;
import com.example.sgswimming.repositories.SwimmerRepository;
import com.example.sgswimming.web.DTOs.read.LessonReadDto;
import com.example.sgswimming.web.DTOs.save.LessonSaveDto;
import com.example.sgswimming.web.DTOs.update.LessonUpdateDto;
import com.example.sgswimming.web.mappers.LessonMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;
    private final InstructorRepository instructorRepository;
    private final SwimmerRepository swimmerRepository;

    private final LessonMapper mapper = LessonMapper.getInstance();

    @Override
    public List<LessonReadDto> findAll() {
        return lessonRepository.findAll().stream().map(mapper::toReadDto).collect(Collectors.toList());
    }

    @Override
    public LessonReadDto findById(Long id) {
        Lesson lesson = lessonRepository.findById(id).orElseThrow(() -> new NotFoundException(id, Instructor.class));
        return mapper.toReadDto(lesson);
    }

    @Override
    public LessonReadDto save(LessonSaveDto dto) {
        Lesson lesson = mapper.fromSaveDtoToLesson(dto);
        Lesson savedLesson = lessonRepository.save(lesson);
        return mapper.toReadDto(savedLesson);
    }

    @Override
    public LessonReadDto update(LessonUpdateDto dto) {

        if (dto.getId() == null) throw new IllegalArgumentException("LessonUpdateDto has to contain an id value.");
        lessonRepository.findById(dto.getId()).orElseThrow(() -> new NotFoundException(dto.getId(), Lesson.class));

        Lesson lesson = mapper.fromUpdateDtoToLesson(dto);
        Lesson updatedLesson = lessonRepository.save(lesson);
        return mapper.toReadDto(updatedLesson);
    }

    @Override
    public void deleteById(Long id) {
        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id, Instructor.class));

        Set<Instructor> instructors = instructorRepository.findAllByLessonsId(id);
        instructors.forEach(instructor -> {
            List<Lesson> lessons = instructor.getLessons();
            lessons.remove(lesson);
            instructor.setLessons(lessons);
        });
        instructorRepository.saveAll(instructors);

        Set<Swimmer> swimmers = swimmerRepository.findAllByLessonsId(id);
        swimmers.forEach(swimmer -> {
            List<Lesson> lessons = swimmer.getLessons();
            lessons.remove(lesson);
            swimmer.setLessons(lessons);
        });
        swimmerRepository.saveAll(swimmers);

        lessonRepository.deleteById(id);
    }
}
