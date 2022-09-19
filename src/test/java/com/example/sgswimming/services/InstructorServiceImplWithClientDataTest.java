package com.example.sgswimming.services;

import com.example.sgswimming.model.ClientData;
import com.example.sgswimming.model.Instructor;
import com.example.sgswimming.model.Lesson;
import com.example.sgswimming.model.exceptions.NotFoundException;
import com.example.sgswimming.repositories.ClientDataRepository;
import com.example.sgswimming.repositories.InstructorRepository;
import com.example.sgswimming.repositories.LessonRepository;
import com.example.sgswimming.repositories.SwimmerRepository;
import com.example.sgswimming.web.DTOs.InstructorFatDto;
import com.example.sgswimming.web.DTOs.InstructorSkinnyDto;
import com.example.sgswimming.web.mappers.InstructorMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.Mapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class InstructorServiceImplWithClientDataTest {

    @Mock
    InstructorRepository instructorRepository;

    @Mock
    LessonRepository lessonRepository;

    InstructorMapper mapper = InstructorMapper.getInstance();

    @Mock
    ClientDataRepository clientDataRepository;

    @InjectMocks
    InstructorServiceImpl instructorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAllWithClientData() {

        ClientData clientData = ClientData.builder().build();
        clientData.setInstructor(new Instructor());

        when(clientDataRepository.findById(anyLong())).thenReturn(Optional.of(clientData));

        List<InstructorFatDto> dtos = instructorService.findAll(1L);

        assertNotNull(dtos);
        assertEquals(1, dtos.size());
    }

    @Test
    void findByIdWithClientData() {
        ClientData clientData = ClientData.builder().build();
        clientData.setInstructor(Instructor.builder().id(1L).build());

        when(clientDataRepository.findById(anyLong())).thenReturn(Optional.of(clientData));

        InstructorFatDto dto = instructorService.findById(1L, 1L);

        assertNotNull(dto);
    }

    @Test
    void saveWithClientData() {

        Long ANY_ID = 1L;

        //given
        List<Lesson> lessons = List.of(
                Lesson.builder().id(1L).build(),
                Lesson.builder().id(2L).build(),
                Lesson.builder().id(3L).build());

        Instructor instructor = new Instructor();
        instructor.setLessons(lessons);

        ClientData clientData = ClientData.builder().id(1L).build();
        clientData.setInstructor(instructor);

        InstructorSkinnyDto dto = InstructorSkinnyDto.builder()
                .firstName("A")
                .lastName("A")
                .lessonIds(List.of(4L, 5L, 6L))
                .build();

        //when
        when(clientDataRepository.findById(any())).thenReturn(Optional.of(clientData));
        when(instructorRepository.findById(anyLong())).thenReturn(Optional.of(instructor));
        when(lessonRepository.findById(anyLong()))
                .thenReturn(Optional.of(Lesson.builder().id(4L).build()))
                .thenReturn(Optional.of(Lesson.builder().id(5L).build()))
                .thenReturn(Optional.of(Lesson.builder().id(6L).build()));

        when(clientDataRepository.save(any())).thenReturn(clientData);
        when(instructorRepository.save(any())).thenReturn(MOCKdemapAndLoadEntities(dto));

        //then
        InstructorFatDto instructorFatDto = instructorService.saveOrUpdate(ANY_ID, dto);

        assertFalse(instructorFatDto.getLessons().stream().anyMatch(lessonSkinnyDto -> lessonSkinnyDto.getId().equals(1L)));
        assertFalse(instructorFatDto.getLessons().stream().anyMatch(lessonSkinnyDto -> lessonSkinnyDto.getId().equals(2L)));
        assertFalse(instructorFatDto.getLessons().stream().anyMatch(lessonSkinnyDto -> lessonSkinnyDto.getId().equals(3L)));

        assertTrue(instructorFatDto.getLessons().stream().anyMatch(lessonSkinnyDto -> lessonSkinnyDto.getId().equals(4L)));
        assertTrue(instructorFatDto.getLessons().stream().anyMatch(lessonSkinnyDto -> lessonSkinnyDto.getId().equals(5L)));
        assertTrue(instructorFatDto.getLessons().stream().anyMatch(lessonSkinnyDto -> lessonSkinnyDto.getId().equals(6L)));

        verify(instructorRepository, never()).findById(any());
    }

    @Test
    void updateWithClientData() {
        Long ANY_ID = 1L;

        //given
        List<Lesson> lessons = List.of(
                Lesson.builder().id(1L).build(),
                Lesson.builder().id(2L).build(),
                Lesson.builder().id(3L).build());

        Instructor instructor = new Instructor();
        instructor.setLessons(lessons);

        ClientData clientData = ClientData.builder().id(1L).build();
        clientData.setInstructor(instructor);

        InstructorSkinnyDto dto = InstructorSkinnyDto.builder()
                .id(ANY_ID)
                .firstName("A")
                .lastName("A")
                .lessonIds(List.of(4L, 5L, 6L))
                .build();

        //when
        when(clientDataRepository.findById(any())).thenReturn(Optional.of(clientData));
        when(instructorRepository.findById(anyLong())).thenReturn(Optional.of(instructor));
        when(lessonRepository.findById(anyLong()))
                .thenReturn(Optional.of(Lesson.builder().id(4L).build()))
                .thenReturn(Optional.of(Lesson.builder().id(5L).build()))
                .thenReturn(Optional.of(Lesson.builder().id(6L).build()));

        when(clientDataRepository.save(any())).thenReturn(clientData);
        when(instructorRepository.save(any())).thenReturn(MOCKdemapAndLoadEntities(dto));

        //then
        InstructorFatDto instructorFatDto = instructorService.saveOrUpdate(ANY_ID, dto);

        assertFalse(instructorFatDto.getLessons().stream().anyMatch(lessonSkinnyDto -> lessonSkinnyDto.getId().equals(1L)));
        assertFalse(instructorFatDto.getLessons().stream().anyMatch(lessonSkinnyDto -> lessonSkinnyDto.getId().equals(2L)));
        assertFalse(instructorFatDto.getLessons().stream().anyMatch(lessonSkinnyDto -> lessonSkinnyDto.getId().equals(3L)));

        assertTrue(instructorFatDto.getLessons().stream().anyMatch(lessonSkinnyDto -> lessonSkinnyDto.getId().equals(4L)));
        assertTrue(instructorFatDto.getLessons().stream().anyMatch(lessonSkinnyDto -> lessonSkinnyDto.getId().equals(5L)));
        assertTrue(instructorFatDto.getLessons().stream().anyMatch(lessonSkinnyDto -> lessonSkinnyDto.getId().equals(6L)));

        verify(instructorRepository, times(1)).findById(any());
    }

    private Instructor MOCKdemapAndLoadEntities(InstructorSkinnyDto instructorDTO) {
        Instructor instructor = mapper.fromSkinnyToInstructor(instructorDTO);

        instructorDTO.getLessonIds()
                .stream()
                .map(id -> Lesson.builder().id(id).build())
                .forEach((lesson) -> {
                    lesson.setInstructor(instructor);
                    instructor.addLesson(lesson);
                });

        return instructor;
    }

    @Test
    void deleteWithClientData() {

        //given
        List<Lesson> lessons = List.of(
                Lesson.builder().id(1L).build(),
                Lesson.builder().id(2L).build(),
                Lesson.builder().id(3L).build());

        Instructor instructor = Instructor.builder().id(1L).build();
        instructor.setLessons(lessons);

        ClientData clientData = ClientData.builder().id(1L).build();
        clientData.setInstructor(instructor);

        //when
        when(clientDataRepository.findById(anyLong())).thenReturn(Optional.of(clientData));
        when(instructorRepository.findById(anyLong())).thenReturn(Optional.of(instructor));

        //then
        instructorService.deleteById(clientData.getId(), 1L);

        assertTrue(clientData.getInstructors().isEmpty());
        assertTrue(clientData.getSwimmers().isEmpty());
        assertTrue(clientData.getLessons().isEmpty());
    }
}
