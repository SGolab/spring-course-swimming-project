package com.example.sgswimming.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NamedEntityGraph(
        name = "lesson-dto-read",
        attributeNodes = {
                @NamedAttributeNode("instructor"),
                @NamedAttributeNode("swimmers"),
        })

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
    @JoinColumn(name = "instructor_id", referencedColumnName = "id")
    private Instructor instructor;

    @Builder.Default
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            joinColumns = @JoinColumn(name = "lesson_id"),
            inverseJoinColumns = @JoinColumn(name = "swimmer_id"))
    private List<Swimmer> swimmers = new ArrayList<>();

    private String description;

    private Integer advanceLevel;

    private LocalDateTime localDateTime;

    public void addSwimmer(Swimmer swimmer) {
        if (swimmers == null) {
            swimmers = new ArrayList<>();
        }
        swimmers.add(swimmer);
    }
}
