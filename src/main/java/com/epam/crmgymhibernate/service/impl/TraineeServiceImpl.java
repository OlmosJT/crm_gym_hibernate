package com.epam.crmgymhibernate.service.impl;

import com.epam.crmgymhibernate.dto.request.UpdateTraineeProfileRequest;
import com.epam.crmgymhibernate.dto.universal.TraineeProfileDto;
import com.epam.crmgymhibernate.model.Trainee;
import com.epam.crmgymhibernate.model.UserEntity;
import com.epam.crmgymhibernate.repository.GenericRepository;
import com.epam.crmgymhibernate.repository.TraineeRepository;
import com.epam.crmgymhibernate.service.TraineeService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class TraineeServiceImpl implements TraineeService {

    private final GenericRepository<UserEntity> userRepository;
    private final TraineeRepository traineeRepository;

    public TraineeServiceImpl(GenericRepository<UserEntity> userRepository, TraineeRepository traineeRepository) {
        this.userRepository = userRepository;
        this.traineeRepository = traineeRepository;
    }

    @Override
    public TraineeProfileDto getTraineeProfile(String username) {
        return null;
    }

    @Override
    public void updateTraineeProfile(UpdateTraineeProfileRequest request) {

    }

    @Override
    public void deleteTraineeProfile(String username) throws EntityNotFoundException {
        traineeRepository.deleteTraineeByUsername(username);
    }
}
