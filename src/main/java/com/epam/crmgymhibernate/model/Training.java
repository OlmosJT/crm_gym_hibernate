package com.epam.crmgymhibernate.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

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
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private LocalDate trainingDate;
    @Column(nullable = false)
    private Integer trainingDuration;
}
