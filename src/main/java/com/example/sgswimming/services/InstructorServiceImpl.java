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
import com.example.sgswimming.web.DTOs.InstructorFatDto;
import com.example.sgswimming.web.DTOs.InstructorSkinnyDto;
import com.example.sgswimming.web.mappers.InstructorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
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
    public List<InstructorFatDto> findAll() {
        return instructorRepository.findAll()
                .stream()
                .map(mapper::toFatDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<InstructorFatDto> findAll(Long clientDataId) {

        Optional<ClientData> clientDataOptional = clientDataRepository.findById(clientDataId);

        if (clientDataOptional.isPresent()) {
            return clientDataOptional.get()
                    .getInstructors()
                    .stream()
                    .map(mapper::toFatDto)
                    .collect(Collectors.toList());
        } else {
            return null;
        }
    }

    @Override
    public InstructorFatDto findById(Long instructorId) {
        return mapper.toFatDto(instructorRepository.findById(instructorId)
                .orElseThrow(() -> new NotFoundException(instructorId, Instructor.class)));
    }

    @Override
    public InstructorFatDto findById(Long clientDataId, Long instructorId) {

        Optional<ClientData> clientDataOptional = clientDataRepository.findById(clientDataId);

        if (!clientDataOptional.isPresent()) return null;

        Instructor foundInstructor = clientDataOptional.get()
                .getInstructors()
                .stream()
                .filter(instructor -> instructor.getId().equals(instructorId))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(instructorId, Instructor.class));

        return mapper.toFatDto(foundInstructor);
    }

    @Transactional
    @Override
    public InstructorFatDto saveOrUpdate(InstructorSkinnyDto instructorDTO) {
        Instructor savedInstructor = save(instructorDTO);
        return mapper.toFatDto(savedInstructor);
    }

    @Transactional
    @Override
    public InstructorFatDto saveOrUpdate(Long clientDataId, InstructorSkinnyDto instructorDTO) {
        Instructor savedInstructor = save(instructorDTO);

        ClientData clientData = clientDataRepository.findById(clientDataId)
                .orElseThrow(() -> new NotFoundException(clientDataId, ClientData.class));
        clientData.setInstructor(savedInstructor);
        clientDataRepository.save(clientData);

        return mapper.toFatDto(savedInstructor);
    }

    private Instructor save(InstructorSkinnyDto instructorDto) {

        if (instructorDto.getId() != null) { //update
            findById(instructorDto.getId()); //check if entity to update exists
        }

        Instructor instructor = demapAndLoadEntities(instructorDto);
        return instructorRepository.save(instructor);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        Instructor instructor = instructorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id, Instructor.class));

        instructor.getLessons().forEach(lesson -> lesson.setInstructor(null));
        instructorRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void deleteById(Long clientDataId, Long instructorId) {
        deleteById(instructorId);

        Optional<ClientData> clientDataOptional = clientDataRepository.findById(clientDataId);
        if (!clientDataOptional.isPresent()) {
            throw new NotFoundException(clientDataId, ClientData.class);
        }
        ClientData clientData = clientDataOptional.get();
        clientData.setInstructor(null);
    }

    private Instructor demapAndLoadEntities(InstructorSkinnyDto instructorDTO) {
        Instructor instructor = mapper.fromSkinnyToInstructor(instructorDTO);

        instructorDTO.getLessonIds()
                .stream()
                .map((id) -> lessonRepository.findById(id).orElseThrow(() -> new NotFoundException(id, Lesson.class)))
                .forEach((lesson) -> {
                    lesson.setInstructor(instructor);
                    instructor.addLesson(lesson);
                });

        return instructor;
    }
}
