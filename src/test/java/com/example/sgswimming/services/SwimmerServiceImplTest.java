package com.example.sgswimming.services;

import com.example.sgswimming.DTOs.SwimmerDTO;
import com.example.sgswimming.model.NotFoundException;
import com.example.sgswimming.model.Swimmer;
import com.example.sgswimming.repositories.SwimmerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class SwimmerServiceImplTest {

    @Mock
    SwimmerRepository swimmerRepository;

    @InjectMocks
    SwimmerServiceImpl swimmerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll() {
        when(swimmerRepository.findAll()).thenReturn(List.of(new Swimmer(), new Swimmer()));

        List<SwimmerDTO> Swimmers = swimmerService.findAll();

        assertNotNull(Swimmers);
        assertEquals(2, Swimmers.size());
    }

    @Test
    void findById() {
        when(swimmerRepository.findById(anyLong())).thenReturn(Optional.of(new Swimmer()));

        SwimmerDTO swimmer = swimmerService.findById(1L);

        assertNotNull(swimmer);
    }

    @Test()
    void findByIdNotFound() {
        when(swimmerRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> swimmerService.findById(1L));
    }
}