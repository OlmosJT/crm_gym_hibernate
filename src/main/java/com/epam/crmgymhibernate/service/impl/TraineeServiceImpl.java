package com.epam.crmgymhibernate.service.impl;

import com.epam.crmgymhibernate.dto.request.UpdateTraineeProfileRequest;
import com.epam.crmgymhibernate.dto.response.TrainersListResponse;
import com.epam.crmgymhibernate.dto.universal.TraineeProfileDto;
import com.epam.crmgymhibernate.dto.universal.TrainingTypeDto;
import com.epam.crmgymhibernate.model.Trainee;
import com.epam.crmgymhibernate.model.UserEntity;
import com.epam.crmgymhibernate.repository.GenericRepository;
import com.epam.crmgymhibernate.repository.TraineeRepository;
import com.epam.crmgymhibernate.repository.UserRepository;
import com.epam.crmgymhibernate.service.TraineeService;
import jakarta.persistence.EntityExistsException;
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

    public TraineeServiceImpl(UserRepository userRepository, TraineeRepository traineeRepository) {
        this.userRepository = userRepository;
        this.traineeRepository = traineeRepository;
    }

    @Override
    public TraineeProfileDto getTraineeProfile(String username) throws EntityNotFoundException {
        Trainee entity = traineeRepository.findTraineeByUsername(username).orElseThrow(() -> {
            throw new EntityNotFoundException("Trainee not found with username: " + username);
        });

        return getTraineeProfileDto(entity);
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

        return getTraineeProfileDto(traineeEntity);

    }

    private TraineeProfileDto getTraineeProfileDto(Trainee traineeEntity) {
        return new TraineeProfileDto(
                traineeEntity.getUser().getUsername(),
                traineeEntity.getUser().getFirstName(),
                traineeEntity.getUser().getLastName(),
                traineeEntity.getDateOfBirth(),
                traineeEntity.getUser().getAddress(),
                traineeEntity.getUser().isActive(),
                traineeEntity.getTrainers().stream().map(trainer -> new TrainersListResponse(
                        trainer.getUser().getUsername(),
                        trainer.getUser().getFirstName(),
                        trainer.getUser().getLastName(),
                        trainer.getSpecializations().stream().map(trainingType -> new TrainingTypeDto(trainingType.getId(), trainingType.getName())).toList()
                )).toList()
        );
    }

    @Override
    public void deleteTraineeProfile(String username) throws EntityNotFoundException {
        traineeRepository.deleteTraineeByUsername(username);
    }
}
