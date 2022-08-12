package com.example.swimmingproject.services;

import com.example.swimmingproject.model.Swimmer;
import com.example.swimmingproject.repositories.SwimmerRepository;
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
    SwimmerRepository SwimmerRepository;

    @InjectMocks
    SwimmerServiceImpl SwimmerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll() {
        when(SwimmerRepository.findAll()).thenReturn(List.of(new Swimmer(), new Swimmer()));

        List<Swimmer> Swimmers = SwimmerService.findAll();

        assertNotNull(Swimmers);
        assertEquals(2, Swimmers.size());
    }

    @Test
    void findById() {
        when(SwimmerRepository.findById(anyLong())).thenReturn(Optional.of(new Swimmer()));

        Swimmer swimmer = SwimmerService.findById(1L);

        assertNotNull(swimmer);
    }
}