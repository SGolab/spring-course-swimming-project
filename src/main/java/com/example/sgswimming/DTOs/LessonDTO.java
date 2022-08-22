package com.example.sgswimming.DTOs;

import com.example.sgswimming.model.Instructor;
import com.example.sgswimming.model.Swimmer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LessonDTO {

    private Long id;
    private Instructor instructor;
    private List<Swimmer> swimmers = new ArrayList<>();
    private String description;
    private LocalDateTime localDateTime;
}
