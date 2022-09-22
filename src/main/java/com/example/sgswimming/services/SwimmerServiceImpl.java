package com.example.sgswimming.services;

import com.example.sgswimming.model.Lesson;
import com.example.sgswimming.model.Swimmer;
import com.example.sgswimming.model.exceptions.NotFoundException;
import com.example.sgswimming.repositories.LessonRepository;
import com.example.sgswimming.repositories.SwimmerRepository;
import com.example.sgswimming.web.DTOs.read.SwimmerReadDto;
import com.example.sgswimming.web.DTOs.save.SwimmerSaveDto;
import com.example.sgswimming.web.DTOs.update.SwimmerUpdateDto;
import com.example.sgswimming.web.mappers.SwimmerMapper;
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
    public List<SwimmerReadDto> findAll() {
        return null;
    }

    @Override
    public SwimmerReadDto findById(Long id) {
        return null;
    }

    @Override
    public SwimmerReadDto save(SwimmerSaveDto swimmerDTO) {
        return null;
    }

    @Override
    public SwimmerReadDto update(SwimmerUpdateDto swimmerDTO) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
