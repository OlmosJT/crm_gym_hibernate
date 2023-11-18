package com.epam.crmgymhibernate.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString @Getter @Setter
public class Training {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Trainee trainee;
    @ManyToOne
    private Trainer trainer;
    @Column(nullable = false)
    private String trainingName;
    @ManyToOne
    private TrainingType trainingType;
    @Column(nullable = false)
    private LocalDateTime trainingDate;
    @Column(nullable = false)
    private Duration trainingDuration;
}
