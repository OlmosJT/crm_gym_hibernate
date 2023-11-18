package com.epam.crmgymhibernate.repository;

import com.epam.crmgymhibernate.model.Trainee;
import com.epam.crmgymhibernate.model.Trainer;
import com.epam.crmgymhibernate.model.Training;
import com.epam.crmgymhibernate.model.TrainingType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface TrainingRepository extends GenericRepository<Training> {
    List<Trainee> findTraineesAssignedOnTrainer(String trainerUsername);
    List<Trainer> findTrainersAssignedOnTrainee(String traineeUsername);
    List<Trainer> findActiveTrainersNotAssignedOnTrainee(String traineeUsername);

    List<Training> findTrainingsOfTrainee(String traineeUsername,
                                          LocalDateTime periodFrom,
                                          LocalDateTime periodTo,
                                          String trainerName,
                                          TrainingType trainingType
    );

    List<Training> findTrainingsOfTrainer(String trainerUsername,
                                          LocalDateTime periodFrom,
                                          LocalDateTime periodTo,
                                          String traineeName
    );

}
