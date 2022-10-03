package com.example.sgswimming.services;

import com.example.sgswimming.model.ClientData;
import com.example.sgswimming.model.Instructor;
import com.example.sgswimming.model.Lesson;
import com.example.sgswimming.model.Swimmer;
import com.example.sgswimming.model.exceptions.NotFoundException;
import com.example.sgswimming.repositories.ClientDataRepository;
import com.example.sgswimming.repositories.InstructorRepository;
import com.example.sgswimming.repositories.LessonRepository;
import com.example.sgswimming.repositories.SwimmerRepository;
import com.example.sgswimming.web.DTOs.read.LessonReadDto;
import com.example.sgswimming.web.DTOs.save.LessonSaveDto;
import com.example.sgswimming.web.DTOs.update.LessonUpdateDto;
import com.example.sgswimming.web.mappers.LessonMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;
    private final InstructorRepository instructorRepository;
    private final SwimmerRepository swimmerRepository;
    private final ClientDataRepository clientDataRepository;

    private final LessonMapper mapper = LessonMapper.getInstance();

    @Override
    public List<LessonReadDto> findAll() {
        return lessonRepository.findAll().stream().map(mapper::toReadDto).collect(Collectors.toList());
    }

    @Override
    public List<LessonReadDto> findAll(ClientData clientData) {
        clientData = reloadClientData(clientData);

        Set<Long> lessonIds = clientData
                .getLessonsCalculated()
                .stream()
                .map(Lesson::getId)
                .collect(Collectors.toSet());

        List<Lesson> lessons = lessonRepository.findAllById(lessonIds);

        return lessons.stream().map(mapper::toReadDto).collect(Collectors.toList());
    }

    @Override
    public LessonReadDto findById(Long id) {
        Lesson lesson = lessonRepository.findById(id).orElseThrow(() -> new NotFoundException(id, Instructor.class));
        return mapper.toReadDto(lesson);
    }

    @Override
    public LessonReadDto findById(ClientData clientData, Long id) {
        clientData = reloadClientData(clientData);

        Optional<Lesson> lesson =
                (isAuthorized(clientData, id)) ? lessonRepository.findById(id) : Optional.empty();

        return mapper.toReadDto(lesson.orElseThrow(() -> new NotFoundException(id, Lesson.class)));
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

        if (lesson.getInstructor() != null) {
            Long instructorId = lesson.getInstructor().getId();

            Instructor instructor = instructorRepository.findById(instructorId)
                    .orElseThrow(() -> new NotFoundException(id, Instructor.class));
            Set<Lesson> lessons = instructor.getLessons();
            lessons.remove(lesson);
            instructor.setLessons(lessons);
            instructorRepository.save(instructor);
        }

        if (CollectionUtils.isEmpty(lesson.getSwimmers())) {
            Set<Long> swimmerIds = lesson.getSwimmers().stream().map(Swimmer::getId).collect(Collectors.toSet());

            List<Swimmer> swimmers = swimmerRepository.findAllById(swimmerIds);
            swimmers.forEach(swimmer -> {
                Set<Lesson> lessons = swimmer.getLessons();
                lessons.remove(lesson);
                swimmer.setLessons(lessons);
            });
            swimmerRepository.saveAll(swimmers);
        }

        lessonRepository.deleteById(id);
    }

    private boolean isAuthorized(ClientData clientData, Long id) {
        return clientData.getLessonsCalculated().stream()
                .anyMatch(lesson -> lesson.getId().equals(id));
    }

    private ClientData reloadClientData(ClientData clientData) {
        Optional<ClientData> clientDataOptional;

        if (clientData.getInstructor() != null) {
            clientDataOptional = clientDataRepository.findByIdForInstructorUser(clientData.getId());
        } else {
            clientDataOptional = clientDataRepository.findByIdForSwimmerUser(clientData.getId());
        }

        return clientDataOptional.orElseThrow(() -> new NotFoundException(clientData.getId(), ClientData.class));
    }
}
