package com.epam.crmgymhibernate.repository;

import com.epam.crmgymhibernate.model.Trainee;
import com.epam.crmgymhibernate.model.Trainer;

import java.util.List;
import java.util.Optional;

public interface TraineeRepository extends GenericRepository<Trainee> {
    Optional<Trainee> findTraineeByUsername(String username);
    void deleteTraineeByUsername(String username);

    Trainee updateTrainee(Trainee entity);
}
