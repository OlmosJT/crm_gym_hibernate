package com.epam.crmgymhibernate.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString @Getter @Setter
public class Trainer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany
    private List<TrainingType> specializations;
    @OneToOne
    private UserEntity user;

    @OneToMany(mappedBy = "trainer")
    private Set<Training> trainings;
}
