package com.example.sgswimming.services;

import com.example.sgswimming.model.ClientData;
import com.example.sgswimming.model.Instructor;
import com.example.sgswimming.model.Lesson;
import com.example.sgswimming.model.Swimmer;
import com.example.sgswimming.model.exceptions.NotFoundException;
import com.example.sgswimming.repositories.ClientDataRepository;
import com.example.sgswimming.repositories.LessonRepository;
import com.example.sgswimming.repositories.SwimmerRepository;
import com.example.sgswimming.web.DTOs.read.InstructorReadDto;
import com.example.sgswimming.web.DTOs.read.SwimmerReadDto;
import com.example.sgswimming.web.DTOs.save.SwimmerSaveDto;
import com.example.sgswimming.web.DTOs.update.SwimmerUpdateDto;
import com.example.sgswimming.web.mappers.SwimmerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SwimmerServiceImpl implements SwimmerService {

    private final SwimmerRepository swimmerRepository;
    private final LessonRepository lessonRepository;

    private final SwimmerMapper mapper = SwimmerMapper.getInstance();

    @Override
    public List<SwimmerReadDto> findAll() {
        return swimmerRepository.findAll().stream().map(mapper::toReadDto).collect(Collectors.toList());
    }

    @Override
    public List<SwimmerReadDto> findAll(ClientData clientData) {
        return swimmerRepository.findAllByClientDataSet(clientData)
                .stream().map(mapper::toReadDto).collect(Collectors.toList());
    }

    @Override
    public SwimmerReadDto findById(Long id) {
        Swimmer swimmer = swimmerRepository.findById(id).orElseThrow(() -> new NotFoundException(id, Instructor.class));
        return mapper.toReadDto(swimmer);
    }

    @Override
    public SwimmerReadDto findById(Long id, ClientData clientData) {
        Swimmer swimmer = swimmerRepository.findByIdAndClientDataSet(id, clientData).orElseThrow(() -> new NotFoundException(id, Instructor.class));
        return mapper.toReadDto(swimmer);
    }

    @Override
    public SwimmerReadDto save(SwimmerSaveDto swimmerDTO) {
        Swimmer swimmer = mapper.fromSaveDtoToSwimmer(swimmerDTO);
        Swimmer savedInstructor = swimmerRepository.save(swimmer);
        return mapper.toReadDto(savedInstructor);
    }

    @Override
    public SwimmerReadDto update(SwimmerUpdateDto swimmerDTO) {
        if (swimmerDTO.getId() == null) {
            throw new IllegalArgumentException("InstructorUpdateDto has to contain an id value.");
        }

        swimmerRepository.findById(swimmerDTO.getId())
                .orElseThrow(() -> new NotFoundException(swimmerDTO.getId(), Instructor.class));

        Swimmer swimmer = mapper.fromUpdateDtoToSwimmer(swimmerDTO);

        if (!swimmerDTO.getLessons().isEmpty()) {
            Set<Lesson> lessons = new HashSet<>(lessonRepository.findAllById(swimmerDTO.getLessons()));
            lessons.forEach(lesson -> lesson.addSwimmer(swimmer));
            swimmer.setLessons(lessons);
        }

        Swimmer savedSwimmer = swimmerRepository.save(swimmer);
        return mapper.toReadDto(savedSwimmer);
    }

    @Override
    public void deleteById(Long id) {
        Swimmer swimmer = swimmerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id, Instructor.class));

        Set<Lesson> lessons = lessonRepository.findAllBySwimmersId(id);
        lessons.forEach(lesson -> {

            List<Swimmer> swimmers = lesson.getSwimmers();
            swimmers.remove(swimmer);
            lesson.setSwimmers(swimmers);
        });

        lessonRepository.saveAll(lessons);

        swimmerRepository.deleteById(id);
    }
}
