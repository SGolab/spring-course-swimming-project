package com.example.sgswimming.services;

import com.example.sgswimming.DTOs.LessonDTO;
import com.example.sgswimming.mappers.LessonMapper;
import com.example.sgswimming.model.Instructor;
import com.example.sgswimming.model.exceptions.NotFoundException;
import com.example.sgswimming.model.Lesson;
import com.example.sgswimming.repositories.InstructorRepository;
import com.example.sgswimming.repositories.LessonRepository;
import com.example.sgswimming.repositories.SwimmerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Service
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;
    private final InstructorRepository instructorRepository;
    private final SwimmerRepository swimmerRepository;

    private final LessonMapper fatMapper = LessonMapper.INSTANCE;
    private final LessonMapper.Skinny skinnyMapper = LessonMapper.Skinny.INSTANCE;

    @Override
    public List<LessonDTO> findAll() {
        return lessonRepository
                .findAll()
                .stream()
                .map(fatMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public LessonDTO findById(Long id) {
        return fatMapper.toDto(lessonRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id, Lesson.class)));
    }

    @Override
    public LessonDTO saveOrUpdate(LessonDTO.Skinny dto) {

        if (dto.getId() != null) { //update
            findById(dto.getId()); //check if entity to update exists
        }

        Lesson lesson = skinnyMapper.fromSkinnyToLesson(dto);

        Stream.of(dto.getInstructorId())
                .map((id) -> instructorRepository.findById(id).orElseThrow(() -> new NotFoundException(id, Instructor.class)))
                .forEach((instructor) -> {
                    instructor.addLesson(lesson);
                    lesson.setInstructor(instructor);
                });

        dto.getSwimmerIds()
                .stream().map((id) -> swimmerRepository.findById(id).orElseThrow(() -> new NotFoundException(id, Instructor.class)))
                .forEach((swimmer) -> {
                    lesson.addSwimmer(swimmer);
                    swimmer.addLesson(lesson);
                });

        Lesson savedLesson = lessonRepository.save(lesson);

        return fatMapper.toDto(savedLesson);
    }

    @Override
    public void deleteById(Long id) {
        lessonRepository.deleteById(id);
    }
}
