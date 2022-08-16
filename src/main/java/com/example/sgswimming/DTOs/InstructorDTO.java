package com.example.sgswimming.DTOs;

import com.example.sgswimming.model.Lesson;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class InstructorDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private List<Lesson> lessons;
}
