package com.epam.crmgymhibernate.service.impl;

import com.epam.crmgymhibernate.dto.request.AddTrainingRequest;
import com.epam.crmgymhibernate.dto.request.GetTrainingsOfTraineeRequest;
import com.epam.crmgymhibernate.dto.request.GetTrainingsOfTrainerRequest;
import com.epam.crmgymhibernate.dto.response.TrainingResponse;
import com.epam.crmgymhibernate.dto.universal.TrainingTypeDto;
import com.epam.crmgymhibernate.model.Trainee;
import com.epam.crmgymhibernate.model.Trainer;
import com.epam.crmgymhibernate.model.Training;
import com.epam.crmgymhibernate.model.TrainingType;
import com.epam.crmgymhibernate.repository.TraineeRepository;
import com.epam.crmgymhibernate.repository.TrainerRepository;
import com.epam.crmgymhibernate.repository.TrainingRepository;
import com.epam.crmgymhibernate.repository.TrainingTypeRepository;
import com.epam.crmgymhibernate.service.TrainingService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainingServiceImpl implements TrainingService {

    private final TrainingRepository trainingRepository;
    private final TrainerRepository trainerRepository;
    private final TraineeRepository traineeRepository;
    private final TrainingTypeRepository trainingTypeRepository;

    public TrainingServiceImpl(TrainingRepository trainingRepository, TrainerRepository trainerRepository, TraineeRepository traineeRepository, TrainingTypeRepository trainingTypeRepository) {
        this.trainingRepository = trainingRepository;
        this.trainerRepository = trainerRepository;
        this.traineeRepository = traineeRepository;
        this.trainingTypeRepository = trainingTypeRepository;
    }

    @Override
    public void addTraining(AddTrainingRequest request) throws EntityNotFoundException {
        Trainee trainee = traineeRepository.findTraineeByUsername(request.traineeUsername()).orElseThrow(() ->
                new EntityNotFoundException("Trainee not found with username: " + request.traineeUsername()));

        Trainer trainer = trainerRepository.findTrainerByUsername(request.trainerUsername()).orElseThrow(() ->
                new EntityNotFoundException("Trainer not found with username: " + request.trainerUsername()));

        Training training = new Training();

        training.setTrainee(trainee);
        training.setTrainer(trainer);

//    FIXME: request doesn't have 'trainingType'. Should Trainer.class be single TrainingType?
        training.setTrainingType(trainer.getSpecializations().get(0));

        training.setTrainingName(request.trainingName());
        training.setTrainingDate(request.trainingDate());
        training.setTrainingDuration(request.duration());

//  FIXME: should I have to check duplication of training in database first via trainingName, trainingDate, trainer, trainee?
        trainingRepository.insert(training);
    }

    @Override
    public List<TrainingResponse> getTrainingsOfTrainer(GetTrainingsOfTrainerRequest trainerRequest) {
        List<Training> trainingList = trainingRepository.findTrainingsOfTrainer(
                trainerRequest.username(),
                trainerRequest.periodFrom(),
                trainerRequest.periodTo(),
                trainerRequest.traineeUsername()

        );
        return trainingList.stream().map(t -> new TrainingResponse(
                t.getTrainingName(),
                t.getTrainingDate(),
                new TrainingTypeDto(t.getTrainingType().getId(), t.getTrainingType().getName()),
                t.getTrainingDuration(),
                "%s %s".formatted(t.getTrainer().getUser().getFirstName(), t.getTrainer().getUser().getLastName()),
                "%s %s".formatted(t.getTrainee().getUser().getFirstName(), t.getTrainee().getUser().getLastName())
        )).toList();
    }

    @Override
    public List<TrainingResponse> getTrainingsOfTrainee(GetTrainingsOfTraineeRequest traineeRequest) throws EntityNotFoundException {
        traineeRepository.findTraineeByUsername(traineeRequest.username()).orElseThrow(() ->
                new EntityNotFoundException("Trainee not found with username: " + traineeRequest.username()));

        if(traineeRequest.trainerUsername() != null) {
            trainerRepository.findTrainerByUsername(traineeRequest.trainerUsername()).orElseThrow(() ->
                    new EntityNotFoundException("Given Trainer username not found: " + traineeRequest.trainerUsername()));
        }


        TrainingType trainingType = trainingTypeRepository.getById(traineeRequest.trainingType().id());

        if( trainingType == null) {
            throw new EntityNotFoundException("TrainingType is not found by id: " + traineeRequest.trainingType());
        }


        List<Training> trainingList = trainingRepository.findTrainingsOfTrainee(
                traineeRequest.username(),
                traineeRequest.periodFrom(),
                traineeRequest.periodTo(),
                traineeRequest.trainerUsername(),
                trainingType
        );
        return null;
    }

}
