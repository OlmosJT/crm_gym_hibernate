package com.epam.crmgymhibernate.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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
    @ManyToMany
    @JoinTable(name = "trainer_trainees",
            joinColumns = @JoinColumn(name = "trainer_id"),
            inverseJoinColumns = @JoinColumn(name = "trainee_id"))
    private List<Trainee> trainees;
}
