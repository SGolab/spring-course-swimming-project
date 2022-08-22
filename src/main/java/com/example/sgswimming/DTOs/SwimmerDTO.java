package com.example.sgswimming.DTOs;

import com.example.sgswimming.model.Lesson;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SwimmerDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private List<Lesson> lessons = new ArrayList<>();
}
