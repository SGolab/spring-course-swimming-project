package com.example.sgswimming.controllers.api.v1;

import com.example.sgswimming.DTOs.LessonDTO;
import com.example.sgswimming.DTOs.SwimmerDTO;
import com.example.sgswimming.services.LessonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class LessonControllerTest {

    @Mock
    LessonService lessonService;

    @InjectMocks
    LessonController lessonController;

    MockMvc mockMvc;

    ObjectMapper objectMapper = JsonMapper.builder()
            .findAndAddModules()
            .build();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(lessonController).build();
    }

    String DESC = "Description";
    LocalDateTime TIME = LocalDateTime.now();

    LessonDTO LESSON = LessonDTO.builder()
            .description(DESC)
            .localDateTime(TIME)
            .swimmer(new SwimmerDTO.Skinny())
            .swimmer(new SwimmerDTO.Skinny())
            .swimmer(new SwimmerDTO.Skinny())
            .build();

    List<LessonDTO> lessons = List.of(
            LESSON,
            new LessonDTO(),
            new LessonDTO());

    @Test
    void getAllLessons() throws Exception {
        when(lessonService.findAll()).thenReturn(lessons);

        mockMvc.perform(get("/api/v1/lessons/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].description", equalTo(DESC)))
                .andExpect(jsonPath("$[0].localDateTime", notNullValue()))
                .andExpect(jsonPath("$[0].swimmers", hasSize(3)));
    }

    @Test
    void getLessonById() throws Exception {
        when(lessonService.findById(anyLong())).thenReturn(LESSON);

        mockMvc.perform(get("/api/v1/lessons/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description", equalTo(DESC)))
                .andExpect(jsonPath("$.localDateTime", notNullValue()))
                .andExpect(jsonPath("$.swimmers", hasSize(3)));
    }

    @Test
    void saveNewLesson() throws Exception {
        when(lessonService.saveOrUpdate(any())).thenReturn(LESSON);

        MockHttpServletRequestBuilder mockRequest = post("/api/v1/lessons/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(LESSON));

        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.description", equalTo(DESC)))
                .andExpect(jsonPath("$.localDateTime", notNullValue()))
                .andExpect(jsonPath("$.swimmers", hasSize(3)));
    }

    @Test
    void updateLesson() throws Exception {
        when(lessonService.saveOrUpdate(any())).thenReturn(LESSON);

        MockHttpServletRequestBuilder mockRequest = put("/api/v1/lessons/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(LESSON));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description", equalTo(DESC)))
                .andExpect(jsonPath("$.localDateTime", notNullValue()))
                .andExpect(jsonPath("$.swimmers", hasSize(3)));
    }

    @Test
    void deleteLessonById() throws Exception {
        mockMvc.perform(delete("/api/v1/lessons/1"))
                .andExpect(status().isOk());

        verify(lessonService).deleteById(anyLong());
    }
}