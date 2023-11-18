package com.epam.crmgymhibernate.service;

import com.epam.crmgymhibernate.dto.request.AddTrainingRequest;
import com.epam.crmgymhibernate.dto.request.GetTrainingsOfTraineeRequest;
import com.epam.crmgymhibernate.dto.request.GetTrainingsOfTrainerRequest;
import com.epam.crmgymhibernate.dto.response.TrainingResponse;

import java.util.List;

public interface TrainingService {
    void addTraining(AddTrainingRequest request);

    List<TrainingResponse> getTrainingsOfTrainer(GetTrainingsOfTrainerRequest trainerRequest);
    List<TrainingResponse> getTrainingsOfTrainee(GetTrainingsOfTraineeRequest traineeRequest);
}
