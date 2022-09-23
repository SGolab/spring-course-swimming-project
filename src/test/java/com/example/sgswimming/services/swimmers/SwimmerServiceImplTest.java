package com.example.sgswimming.services.swimmers;

import com.example.sgswimming.model.Swimmer;
import com.example.sgswimming.model.Swimmer;
import com.example.sgswimming.model.exceptions.NotFoundException;
import com.example.sgswimming.repositories.LessonRepository;
import com.example.sgswimming.repositories.SwimmerRepository;
import com.example.sgswimming.services.SwimmerServiceImpl;
import com.example.sgswimming.web.DTOs.read.SwimmerReadDto;
import com.example.sgswimming.web.DTOs.save.SwimmerSaveDto;
import com.example.sgswimming.web.DTOs.update.SwimmerUpdateDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class SwimmerServiceImplTest {

    @Mock
    SwimmerRepository repository;

    @Mock
    LessonRepository lessonRepository;

    @InjectMocks
    SwimmerServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll() {
        when(repository.findAll()).thenReturn(List.of(new Swimmer(), new Swimmer()));

        List<SwimmerReadDto> swimmers = service.findAll();

        assertNotNull(swimmers);
        assertEquals(2, swimmers.size());
    }

    @Test
    void findById() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(new Swimmer()));

        SwimmerReadDto swimmer = service.findById(1L);

        assertNotNull(swimmer);
    }

    @Test()
    void findByIdNotFound() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(
                NotFoundException.class,
                () -> service.findById(1L));
    }

    @Test
    void save() {
        SwimmerSaveDto swimmerDTO = new SwimmerSaveDto();
        Swimmer swimmer = new Swimmer();

        when(repository.save(any())).thenReturn(swimmer);

        SwimmerReadDto foundSwimmer = service.save(swimmerDTO);

        assertNotNull(foundSwimmer);
        verify(repository).save(any(Swimmer.class));
        verify(repository, never()).findById(anyLong());
    }

    @Test
    void update() {
        SwimmerUpdateDto swimmerDTO = new SwimmerUpdateDto();
        swimmerDTO.setId(1L);

        Swimmer swimmer = new Swimmer();

        when(repository.findById(anyLong())).thenReturn(Optional.of(swimmer));
        when(repository.save(any())).thenReturn(swimmer);

        SwimmerReadDto foundSwimmer = service.update(swimmerDTO);

        assertNotNull(foundSwimmer);
        verify(repository).save(any(Swimmer.class));
        verify(repository).findById(anyLong());
    }

    @Test
    void updateIllegalArgument() {
        SwimmerUpdateDto swimmerDTO = new SwimmerUpdateDto();

        assertThrows(IllegalArgumentException.class, () -> service.update(swimmerDTO));
    }

    @Test
    void updateNotFound() {
        SwimmerUpdateDto swimmerDTO = new SwimmerUpdateDto();
        swimmerDTO.setId(1L);

        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.update(swimmerDTO));
    }


    @Test
    void deleteById() {
        Long id = 1L;

        when(repository.findById(anyLong())).thenReturn(Optional.of(new Swimmer()));

        service.deleteById(id);

        verify(repository).deleteById(anyLong());
        verify(lessonRepository).saveAll(any());
    }
}