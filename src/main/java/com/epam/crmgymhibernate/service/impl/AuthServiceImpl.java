package com.epam.crmgymhibernate.service.impl;

import com.epam.crmgymhibernate.dto.request.RegisterTraineeRequest;
import com.epam.crmgymhibernate.dto.request.RegisterTrainerRequest;
import com.epam.crmgymhibernate.dto.response.RegisterResponse;
import com.epam.crmgymhibernate.model.Trainee;
import com.epam.crmgymhibernate.model.Trainer;
import com.epam.crmgymhibernate.model.TrainingType;
import com.epam.crmgymhibernate.model.UserEntity;
import com.epam.crmgymhibernate.repository.GenericRepository;
import com.epam.crmgymhibernate.service.AuthService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {


    private final GenericRepository<UserEntity> userRepository;
    private final GenericRepository<Trainee> traineeRepository;
    private final GenericRepository<TrainingType> trainingTypeRepository;

    @Autowired
    public AuthServiceImpl(GenericRepository<UserEntity> userRepository, GenericRepository<Trainee> traineeRepository, GenericRepository<TrainingType> trainingTypeRepository) {
        this.userRepository = userRepository;
        this.traineeRepository = traineeRepository;
        this.trainingTypeRepository = trainingTypeRepository;
    }

    @Override
    public RegisterResponse registerTrainee(RegisterTraineeRequest request) throws EntityExistsException {
        UserEntity userEntity = new UserEntity();

        userEntity.setFirstName(request.firstname());
        userEntity.setLastName(request.lastname());
        userEntity.setAddress(request.address());

        userEntity.setUsername(generateUsername(request.firstname(), request.lastname()));
        userEntity.setPassword(generatePassword(10));

        userRepository.insert(userEntity);

        Trainee traineeEntity = new Trainee();
        traineeEntity.setDateOfBirth(request.dateOfBirth());
        traineeEntity.setUser(userEntity);

        traineeRepository.insert(traineeEntity);

        return new RegisterResponse(userEntity.getUsername(), userEntity.getPassword());
    }

    @Override
    public RegisterResponse registerTrainer(RegisterTrainerRequest request) throws EntityNotFoundException {
        UserEntity userEntity = new UserEntity();

        userEntity.setFirstName(request.firstname());
        userEntity.setLastName(request.lastname());

        userEntity.setUsername(generateUsername(request.firstname(), request.lastname()));
        userEntity.setPassword(generatePassword(10));

        userRepository.insert(userEntity);

        Trainer trainerEntity = new Trainer();
        trainerEntity.setUser(userEntity);

        final List<TrainingType> specializations = new ArrayList<>();

        request.specializations().forEach(dto -> {
            var entity = trainingTypeRepository.getById(dto.id());
            if(entity == null) throw new EntityNotFoundException("Specialization for %s is not exist in database.".formatted(dto));
            specializations.add(entity);
        });

        trainerEntity.setSpecializations(specializations);

        return new RegisterResponse(userEntity.getUsername(), userEntity.getPassword());
    }

    private String generateUsername(String firstname, String lastname) {
        return null;
    }

}
