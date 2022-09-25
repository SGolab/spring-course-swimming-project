package com.example.sgswimming.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"instructor", "swimmers"})
@EqualsAndHashCode(exclude = {"instructor", "swimmers"})
@Entity
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "instructor_id")
    private Instructor instructor;

    @Singular
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            joinColumns = @JoinColumn(name = "lesson_id"),
            inverseJoinColumns = @JoinColumn(name = "swimmer_id"))
    private List<Swimmer> swimmers = new ArrayList<>();

    private String description;

    private LocalDateTime localDateTime;

    @Builder
    public Lesson(Long id, Instructor instructor, String description, LocalDateTime localDateTime) {
        this.id = id;
        this.instructor = instructor;
        this.description = description;
        this.localDateTime = localDateTime;
    }

    public void addSwimmer(Swimmer swimmer) {
        swimmers.add(swimmer);
    }
}
