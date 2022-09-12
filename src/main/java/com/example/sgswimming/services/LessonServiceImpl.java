package com.example.sgswimming.services;

import com.example.sgswimming.web.DTOs.LessonFatDto;
import com.example.sgswimming.web.DTOs.LessonSkinnyDto;
import com.example.sgswimming.web.mappers.LessonMapper;
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

    private final LessonMapper mapper = LessonMapper.getInstance();

    @Override
    public List<LessonFatDto> findAll() {
        return lessonRepository
                .findAll()
                .stream()
                .map(mapper::toFatDto)
                .collect(Collectors.toList());
    }

    @Override
    public LessonFatDto findById(Long id) {
        return mapper.toFatDto(lessonRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id, Lesson.class)));
    }

    @Override
    public LessonFatDto saveOrUpdate(LessonSkinnyDto dto) {

        if (dto.getId() != null) { //update
            findById(dto.getId()); //check if entity to update exists
        }

        Lesson lesson = mapper.fromSkinnyToLesson(dto);

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

        return mapper.toFatDto(savedLesson);
    }

    @Override
    public void deleteById(Long id) {
        lessonRepository.deleteById(id);
    }
}
