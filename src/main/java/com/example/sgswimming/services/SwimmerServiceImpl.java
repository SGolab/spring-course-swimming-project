package com.example.sgswimming.services;

import com.example.sgswimming.DTOs.SwimmerFatDto;
import com.example.sgswimming.DTOs.SwimmerSkinnyDto;
import com.example.sgswimming.mappers.SwimmerMapper;
import com.example.sgswimming.model.Lesson;
import com.example.sgswimming.model.Swimmer;
import com.example.sgswimming.model.exceptions.NotFoundException;
import com.example.sgswimming.repositories.LessonRepository;
import com.example.sgswimming.repositories.SwimmerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SwimmerServiceImpl implements SwimmerService {

    private final SwimmerRepository swimmerRepository;
    private final LessonRepository lessonRepository;

    private final SwimmerMapper mapper = SwimmerMapper.getInstance();

    @Override
    public List<SwimmerFatDto> findAll() {
        return swimmerRepository
                .findAll()
                .stream()
                .map(mapper::toFatDto)
                .collect(Collectors.toList());
    }

    @Override
    public SwimmerFatDto findById(Long id) {
        return mapper.toFatDto(swimmerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id, Swimmer.class)));
    }

    @Override
    public SwimmerFatDto saveOrUpdate(SwimmerSkinnyDto swimmerDTO) {

        if (swimmerDTO.getId() != null) { //update
            findById(swimmerDTO.getId()); //check if entity to update exists
        }

        Swimmer swimmer = mapper.fromSkinnyToSwimmer(swimmerDTO);

        swimmerDTO.getLessonIds()
                .stream()
                .map((id) -> lessonRepository.findById(id).orElseThrow(() -> new NotFoundException(id, Lesson.class)))
                .forEach((lesson -> {
                    lesson.addSwimmer(swimmer);
                    swimmer.addLesson(lesson);
                }));

        Swimmer savedSwimmer = swimmerRepository.save(swimmer);

        return mapper.toFatDto(savedSwimmer);
    }

    @Override
    public void deleteById(Long id) {
        swimmerRepository.deleteById(id);
    }
}
