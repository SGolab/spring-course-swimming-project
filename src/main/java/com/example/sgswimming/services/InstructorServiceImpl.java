package com.example.sgswimming.services;

import com.example.sgswimming.model.ClientData;
import com.example.sgswimming.model.Instructor;
import com.example.sgswimming.model.Lesson;
import com.example.sgswimming.model.exceptions.NotFoundException;
import com.example.sgswimming.repositories.ClientDataRepository;
import com.example.sgswimming.repositories.InstructorRepository;
import com.example.sgswimming.repositories.LessonRepository;
import com.example.sgswimming.repositories.SwimmerRepository;
import com.example.sgswimming.web.DTOs.read.InstructorReadDto;
import com.example.sgswimming.web.DTOs.save.InstructorSaveDto;
import com.example.sgswimming.web.DTOs.update.InstructorUpdateDto;
import com.example.sgswimming.web.mappers.InstructorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class InstructorServiceImpl implements InstructorService {

    private final ClientDataRepository clientDataRepository;

    private final InstructorRepository instructorRepository;
    private final LessonRepository lessonRepository;
    private final SwimmerRepository swimmerRepository;

    private final InstructorMapper mapper = InstructorMapper.getInstance();

    @Override
    public List<InstructorReadDto> findAll() {
        return instructorRepository.findAll().stream().map(mapper::toReadDto).collect(Collectors.toList());
    }

    @Override
    public List<InstructorReadDto> findAll(ClientData clientData) {
        return instructorRepository.findAllByClientDataSet(clientData)
                .stream().map(mapper::toReadDto).collect(Collectors.toList());
    }

    @Override
    public InstructorReadDto findById(Long id) {
        Instructor instructor = instructorRepository.findById(id).orElseThrow(() -> new NotFoundException(id, Instructor.class));
        return mapper.toReadDto(instructor);
    }

    @Override
    public InstructorReadDto findById(ClientData clientData, Long id) {
        Instructor instructor = instructorRepository.findByIdAndClientDataSet(id, clientData).orElseThrow(() -> new NotFoundException(id, Instructor.class));
        return mapper.toReadDto(instructor);
    }

    @Override
    public InstructorReadDto save(InstructorSaveDto dto) {
        Instructor instructor = mapper.fromSaveDtoToInstructor(dto);
        Instructor savedInstructor = instructorRepository.save(instructor);
        return mapper.toReadDto(savedInstructor);
    }

    @Override
    public InstructorReadDto update(InstructorUpdateDto dto) {

        if (dto.getId() == null) {
            throw new IllegalArgumentException("InstructorUpdateDto has to contain an id value.");
        }

        instructorRepository.findById(dto.getId())
                .orElseThrow(() -> new NotFoundException(dto.getId(), Instructor.class));

        Instructor instructor = mapper.fromUpdateDtoToInstructor(dto);

        if (!dto.getLessons().isEmpty()) {
            List<Lesson> lessons = lessonRepository.findAllById(dto.getLessons());
            lessons.forEach(lesson -> lesson.setInstructor(instructor));
            instructor.setLessons(lessons);
        }

        Instructor savedInstructor = instructorRepository.save(instructor);
        return mapper.toReadDto(savedInstructor);
    }

    @Override
    public void deleteById(Long id) {

        Set<Lesson> lessons = lessonRepository.findAllByInstructorId(id);
        lessons.forEach(lesson -> lesson.setInstructor(null));
        lessonRepository.saveAll(lessons);

        instructorRepository.deleteById(id);
    }
}
