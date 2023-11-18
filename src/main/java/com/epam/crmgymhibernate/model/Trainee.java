package com.epam.crmgymhibernate.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString @Getter @Setter
public class Trainee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Temporal(TemporalType.DATE)
    private LocalDate dateOfBirth;
    @OneToOne
    private UserEntity user;
    @OneToMany(mappedBy = "trainee", cascade = CascadeType.ALL)
    private Set<Training> trainings;
}
