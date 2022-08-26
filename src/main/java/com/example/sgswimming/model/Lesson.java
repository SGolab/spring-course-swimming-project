package com.example.sgswimming.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "instructor_id")
    private Instructor instructor;

    @Singular
    @ManyToMany
    @JoinTable(
            joinColumns = @JoinColumn(name = "lesson_id"),
            inverseJoinColumns = @JoinColumn(name = "swimmer_id"))
    private List<Swimmer> swimmers = new ArrayList<>();

    private String description;

    private LocalDateTime localDateTime;

    public void addSwimmer(Swimmer swimmer) {
        if (swimmers == null) {
            swimmers = new ArrayList<>();
        }
        swimmers.add(swimmer);
    }
}
