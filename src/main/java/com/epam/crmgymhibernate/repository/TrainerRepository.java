package com.epam.crmgymhibernate.repository;


import com.epam.crmgymhibernate.model.Trainer;

import java.util.List;
import java.util.Optional;

public interface TrainerRepository extends GenericRepository<Trainer> {
    Optional<Trainer> findTrainerByUsername(String username);

    void deleteTrainerByUsername(String username);

    Trainer updateTrainer(Trainer trainerEntity);

    List<Trainer> getActiveNotAssignedTrainers(String username);
}
