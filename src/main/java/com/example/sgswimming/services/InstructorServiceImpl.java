package com.example.sgswimming.services;

import com.example.sgswimming.model.ClientData;
import com.example.sgswimming.model.Instructor;
import com.example.sgswimming.model.Lesson;
import com.example.sgswimming.model.exceptions.NotFoundException;
import com.example.sgswimming.repositories.ClientDataRepository;
import com.example.sgswimming.repositories.InstructorRepository;
import com.example.sgswimming.repositories.LessonRepository;
import com.example.sgswimming.web.DTOs.read.InstructorReadDto;
import com.example.sgswimming.web.DTOs.save.InstructorSaveDto;
import com.example.sgswimming.web.DTOs.update.InstructorUpdateDto;
import com.example.sgswimming.web.mappers.InstructorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class InstructorServiceImpl implements InstructorService {

    private final InstructorRepository instructorRepository;
    private final LessonRepository lessonRepository;
    private final ClientDataRepository clientDataRepository;

    private final InstructorMapper mapper = InstructorMapper.getInstance();

    @Override
    public List<InstructorReadDto> findAll() {
        return instructorRepository.findAll().stream().map(mapper::toReadDto).collect(Collectors.toList());
    }

    @Override
    public List<InstructorReadDto> findAll(ClientData clientData) {
        clientData = reloadClientData(clientData);

        Set<Long> instructorIds = clientData
                .getInstructorsCalculated()
                .stream()
                .map(Instructor::getId)
                .collect(Collectors.toSet());

        List<Instructor> instructors = instructorRepository.findAllById(instructorIds);

        return instructors.stream().map(mapper::toReadDto).collect(Collectors.toList());
    }

    @Override
    public InstructorReadDto findById(Long id) {
        Instructor instructor = instructorRepository.findById(id).orElseThrow(() -> new NotFoundException(id, Instructor.class));
        return mapper.toReadDto(instructor);
    }

    @Override
    public InstructorReadDto findById(ClientData clientData, Long id) {
        clientData = reloadClientData(clientData);

        Optional<Instructor> instructor =
                (isAuthorized(clientData, id)) ? instructorRepository.findById(id) : Optional.empty();

        return mapper.toReadDto(instructor.orElseThrow(() -> new NotFoundException(id, Instructor.class)));
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

       findInstructor(dto.getId()); //throw exception if fails

        Instructor instructor = mapper.fromUpdateDtoToInstructor(dto);

        if (!dto.getLessons().isEmpty()) {
            Set<Lesson> lessons = new HashSet<>(lessonRepository.findAllById(dto.getLessons()));
            lessons.forEach(lesson -> lesson.setInstructor(instructor));
            instructor.setLessons(lessons);
        }

        Instructor savedInstructor = instructorRepository.save(instructor);
        return mapper.toReadDto(savedInstructor);
    }

    @Override
    public void deleteById(Long id) {
        Instructor instructor = findInstructor(id);
        Set<Lesson> lessons = instructor.getLessons();
        lessons.forEach(lesson -> lesson.setInstructor(null));
        lessonRepository.saveAll(lessons);

        instructorRepository.deleteById(id);
    }

    private Instructor findInstructor(Long id) {
        return instructorRepository.findById(id).orElseThrow(() -> new NotFoundException(id, Instructor.class));
    }

    private boolean isAuthorized(ClientData clientData, Long id) {
        return clientData.getInstructorsCalculated().stream().anyMatch(instructor -> instructor.getId().equals(id));
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
