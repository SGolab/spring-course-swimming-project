package com.example.sgswimming.services;

import com.example.sgswimming.web.DTOs.InstructorFatDto;
import com.example.sgswimming.web.DTOs.InstructorSkinnyDto;
import com.example.sgswimming.web.mappers.InstructorMapper;
import com.example.sgswimming.model.Instructor;
import com.example.sgswimming.model.Lesson;
import com.example.sgswimming.model.exceptions.NotFoundException;
import com.example.sgswimming.repositories.InstructorRepository;
import com.example.sgswimming.repositories.LessonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class InstructorServiceImpl implements InstructorService {

    private final InstructorRepository instructorRepository;
    private final LessonRepository lessonRepository;

    private final InstructorMapper mapper = InstructorMapper.getInstance();

    @Override
    public List<InstructorFatDto> findAll() {
        return instructorRepository
                .findAll()
                .stream()
                .map(mapper::toFatDto)
                .collect(Collectors.toList());
    }

    @Override
    public InstructorFatDto findById(Long id) {
        return mapper.toFatDto(instructorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id, Instructor.class)));
    }

    @Override
    public InstructorFatDto saveOrUpdate(InstructorSkinnyDto instructorDTO) {

        if (instructorDTO.getId() != null) { //update
            findById(instructorDTO.getId()); //check if entity to update exists
        }

        Instructor instructor = mapper.fromSkinnyToInstructor(instructorDTO);

        instructorDTO.getLessonIds()
                .stream()
                .map((id) -> lessonRepository.findById(id).orElseThrow(() -> new NotFoundException(id, Lesson.class)))
                .forEach((lesson) -> {
                    lesson.setInstructor(instructor);
                    instructor.addLesson(lesson);
                });

        Instructor savedInstructor = instructorRepository.save(instructor);

        return mapper.toFatDto(savedInstructor);
    }

    @Override
    public void deleteById(Long id) {
        Instructor instructor = instructorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id, Instructor.class));

        instructor.getLessons().forEach(lesson -> lesson.setInstructor(null));
        instructorRepository.deleteById(id);
    }
}
