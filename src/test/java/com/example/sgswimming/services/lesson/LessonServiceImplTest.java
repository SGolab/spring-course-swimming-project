//package com.example.sgswimming.services;
//
//import com.example.sgswimming.model.Instructor;
//import com.example.sgswimming.model.Lesson;
//import com.example.sgswimming.model.Swimmer;
//import com.example.sgswimming.model.exceptions.NotFoundException;
//import com.example.sgswimming.repositories.InstructorRepository;
//import com.example.sgswimming.repositories.LessonRepository;
//import com.example.sgswimming.repositories.SwimmerRepository;
//import com.example.sgswimming.web.DTOs.read.LessonReadDto;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.Mockito.*;
//
//class LessonServiceImplTest {
//
//    @Mock
//    LessonRepository lessonRepository;
//
//    @Mock
//    InstructorRepository instructorRepository;
//
//    @Mock
//    SwimmerRepository swimmerRepository;
//
//    @InjectMocks
//    LessonServiceImpl lessonService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void findAll() {
//        when(lessonRepository.findAll()).thenReturn(List.of(new Lesson(), new Lesson()));
//
//        List<LessonReadDto> Lessons = lessonService.findAll();
//
//        assertNotNull(Lessons);
//        assertEquals(2, Lessons.size());
//    }
//
//    @Test
//    void findById() {
//        when(lessonRepository.findById(anyLong())).thenReturn(Optional.of(new Lesson()));
//
//        LessonReadDto lesson = lessonService.findById(1L);
//
//        assertNotNull(lesson);
//    }
//
//    @Test()
//    void findByIdNotFound() {
//        when(lessonRepository.findById(anyLong())).thenReturn(Optional.empty());
//        assertThrows(NotFoundException.class, () -> lessonService.findById(1L));
//    }
//
//    @Test
//    void save() {
//        LessonSkinnyDto lessonDTO = LessonSkinnyDto.builder().build();
//        lessonDTO.setInstructorId(1L);
//        lessonDTO.setSwimmerIds(List.of(1L, 2L, 3L));
//
//        Lesson lesson = new Lesson();
//
//        when(lessonRepository.save(any(Lesson.class))).thenReturn(lesson);
//        when(instructorRepository.findById(anyLong())).thenReturn(Optional.of(new Instructor()));
//        when(swimmerRepository.findById(anyLong())).thenReturn(Optional.of(new Swimmer()));
//
//        LessonReadDto foundLesson = lessonService.saveOrUpdate(lessonDTO);
//
//        assertNotNull(foundLesson);
//        verify(lessonRepository).save(any(Lesson.class));
//        verify(lessonRepository, never()).findById(anyLong());
//    }
//
//    @Test
//    void update() {
//        LessonSkinnyDto lessonDTO = LessonSkinnyDto.builder().build();
//        lessonDTO.setId(1L);
//        lessonDTO.setInstructorId(1L);
//        lessonDTO.setSwimmerIds(List.of(1L, 2L, 3L));
//
//        Lesson lesson = new Lesson();
//
//        when(lessonRepository.save(any(Lesson.class))).thenReturn(lesson);
//        when(lessonRepository.findById(anyLong())).thenReturn(Optional.of(lesson));
//        when(instructorRepository.findById(anyLong())).thenReturn(Optional.of(new Instructor()));
//        when(swimmerRepository.findById(anyLong())).thenReturn(Optional.of(new Swimmer()));
//
//        LessonReadDto foundLesson = lessonService.saveOrUpdate(lessonDTO);
//
//        assertNotNull(foundLesson);
//        verify(lessonRepository).save(any(Lesson.class));
//        verify(lessonRepository, times(1)).findById(anyLong());
//    }
//
//    @Test
//    void updateLessonNotFound() {
//        LessonUpda lessonDTO = LessonSkinnyDto.builder().build();
//        lessonDTO.setInstructorId(1L);
//        lessonDTO.setSwimmerIds(List.of(1L, 2L, 3L));
//
//        when(lessonRepository.findById(anyLong())).thenReturn(Optional.empty());
//        when(instructorRepository.findById(anyLong())).thenReturn(Optional.of(new Instructor()));
//        when(swimmerRepository.findById(anyLong())).thenReturn(Optional.of(new Swimmer()));
//
//        lessonService.update(lessonDTO);
//        assertThrows(NotFoundException.class, () -> lessonService.findById(1L));
//    }
//
//    @Test
//    void deleteById() {
//
//        Long id = 1L;
//
//        when(lessonRepository.findById(anyLong())).thenReturn(Optional.of(new Lesson()));
//
//        lessonService.deleteById(id);
//
//        verify(lessonRepository).deleteById(anyLong());
//    }
//}