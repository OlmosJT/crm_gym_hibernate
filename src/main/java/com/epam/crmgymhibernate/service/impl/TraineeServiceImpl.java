package com.epam.crmgymhibernate.service.impl;

import com.epam.crmgymhibernate.dto.request.UpdateTraineeProfileRequest;
import com.epam.crmgymhibernate.dto.response.TrainerListResponse;
import com.epam.crmgymhibernate.dto.universal.TraineeProfileDto;
import com.epam.crmgymhibernate.dto.universal.TrainingTypeDto;
import com.epam.crmgymhibernate.model.Trainee;
import com.epam.crmgymhibernate.model.Trainer;
import com.epam.crmgymhibernate.repository.TraineeRepository;
import com.epam.crmgymhibernate.repository.TrainingRepository;
import com.epam.crmgymhibernate.repository.UserRepository;
import com.epam.crmgymhibernate.service.TraineeService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class TraineeServiceImpl implements TraineeService {

    private final UserRepository userRepository;
    private final TraineeRepository traineeRepository;
    private final TrainingRepository trainingRepository;

    public TraineeServiceImpl(UserRepository userRepository, TraineeRepository traineeRepository, TrainingRepository trainingRepository) {
        this.userRepository = userRepository;
        this.traineeRepository = traineeRepository;
        this.trainingRepository = trainingRepository;
    }

    @Override
    public TraineeProfileDto getTraineeProfile(String username) throws EntityNotFoundException {
        Trainee traineeEntity = traineeRepository.findTraineeByUsername(username).orElseThrow(() ->
                new EntityNotFoundException("Trainee not found with username: " + username));

        return getTraineeProfileDto(traineeEntity, trainingRepository.findTrainersAssignedOnTrainee(traineeEntity.getUser().getUsername()));
    }

    @Override
    public TraineeProfileDto updateTraineeProfile(UpdateTraineeProfileRequest request) throws EntityNotFoundException {
        Trainee traineeEntity = traineeRepository.findTraineeByUsername(request.username()).orElseThrow(
                () -> new EntityNotFoundException("Trainee not found with username: " + request.username())
        );
        // logic
        traineeEntity.getUser().setFirstName(request.firstname());
        traineeEntity.getUser().setLastName(request.lastname());
        traineeEntity.getUser().setActive(request.isActive());

        if(request.address() != null) {
            traineeEntity.getUser().setAddress(request.address());
        }
        if(request.dateOfBirth() != null) {
            traineeEntity.setDateOfBirth(request.dateOfBirth());
        }

        if(!Objects.equals(traineeEntity.getUser().getUsername(), request.username())) {
            if(!userRepository.existByUsername(request.username())) {
                traineeEntity.getUser().setUsername(request.username());
            }
        }

        traineeEntity = traineeRepository.updateTrainee(traineeEntity);

        return getTraineeProfileDto(traineeEntity, trainingRepository.findTrainersAssignedOnTrainee(traineeEntity.getUser().getUsername()));

    }

    private TraineeProfileDto getTraineeProfileDto(Trainee traineeEntity, List<Trainer> trainersAssignedToTrainee) {
        return new TraineeProfileDto(
                traineeEntity.getUser().getUsername(),
                traineeEntity.getUser().getFirstName(),
                traineeEntity.getUser().getLastName(),
                traineeEntity.getDateOfBirth(),
                traineeEntity.getUser().getAddress(),
                traineeEntity.getUser().isActive(),
                trainersAssignedToTrainee.stream().map(trainer -> new TrainerListResponse(
                        trainer.getUser().getUsername(),
                        trainer.getUser().getFirstName(),
                        trainer.getUser().getLastName(),
                        trainer.getSpecializations().stream().map(trainingType -> new TrainingTypeDto(trainingType.getId(), trainingType.getName())).toList()
                )).toList()
        );
    }

    @Override
    public List<TrainerListResponse> getActiveTrainersNotAssignedToTrainee(String traineeUsername) {
        List<Trainer> trainerList = trainingRepository.findActiveTrainersNotAssignedOnTrainee(traineeUsername);

        return trainerList.stream().map(trainer -> new TrainerListResponse(
                trainer.getUser().getUsername(),
                trainer.getUser().getFirstName(),
                trainer.getUser().getLastName(),
                trainer.getSpecializations().stream().map(trainingType -> new TrainingTypeDto(trainingType.getId(), trainingType.getName())).toList()
        )).toList();
    }

    @Override
    public void deleteTraineeProfile(String username) throws EntityNotFoundException {
        traineeRepository.deleteTraineeByUsername(username);
    }
}
