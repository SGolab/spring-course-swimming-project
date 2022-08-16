package com.example.sgswimming.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Swimmer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstName;
    private String lastName;

    @ManyToMany(mappedBy = "swimmers")
    private List<Lesson> lessons = new ArrayList<>();

    public void addLesson(Lesson lesson) {
        if (lessons == null) {
            lessons = new ArrayList<>();
        }
        lessons.add(lesson);
    }
}
