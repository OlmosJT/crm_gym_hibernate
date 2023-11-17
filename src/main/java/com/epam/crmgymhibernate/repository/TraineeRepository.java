package com.epam.crmgymhibernate.repository;

import com.epam.crmgymhibernate.model.Trainee;

import java.util.Optional;

public interface TraineeRepository extends GenericRepository<Trainee> {
    Optional<Trainee> findTraineeByUsername(String username);
    void deleteTraineeByUsername(String username);

    Trainee updateTrainee(Trainee entity);
}
