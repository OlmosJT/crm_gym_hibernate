package com.epam.crmgymhibernate.service.impl;

import com.epam.crmgymhibernate.dto.request.ChangePasswordRequest;
import com.epam.crmgymhibernate.dto.request.RegisterTraineeRequest;
import com.epam.crmgymhibernate.dto.request.RegisterTrainerRequest;
import com.epam.crmgymhibernate.dto.response.RegisterResponse;
import com.epam.crmgymhibernate.model.Trainee;
import com.epam.crmgymhibernate.model.Trainer;
import com.epam.crmgymhibernate.model.TrainingType;
import com.epam.crmgymhibernate.model.UserEntity;
import com.epam.crmgymhibernate.repository.GenericRepository;
import com.epam.crmgymhibernate.repository.TraineeRepository;
import com.epam.crmgymhibernate.repository.UserRepository;
import com.epam.crmgymhibernate.service.AuthService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AuthServiceImpl implements AuthService {


    private final UserRepository userRepository;
    private final TraineeRepository traineeRepository;
    private final GenericRepository<Trainer> trainerRepository;
    private final GenericRepository<TrainingType> trainingTypeRepository;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository, TraineeRepository traineeRepository, GenericRepository<Trainer> trainerRepository, GenericRepository<TrainingType> trainingTypeRepository) {
        this.userRepository = userRepository;
        this.traineeRepository = traineeRepository;
        this.trainerRepository = trainerRepository;
        this.trainingTypeRepository = trainingTypeRepository;
    }

    @Override
    public RegisterResponse registerTrainee(RegisterTraineeRequest request) throws EntityExistsException {
        UserEntity userEntity = new UserEntity();

        userEntity.setFirstName(request.firstname());
        userEntity.setLastName(request.lastname());
        userEntity.setAddress(request.address());
        userEntity.setActive(true);

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
        userEntity.setActive(true);

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

        trainerRepository.insert(trainerEntity);

        return new RegisterResponse(userEntity.getUsername(), userEntity.getPassword());
    }

    @Override
    public void changeUserPassword(ChangePasswordRequest request) throws EntityNotFoundException {
        var resultList = userRepository.findByProperty("username", request.username());
        if(resultList.isEmpty()) {
            throw new EntityNotFoundException("User not found with username: " + request.username());
        } else {
            UserEntity entity = resultList.get(0);
            // temporary logic to check password match. Later hash codes need to be checked
            if(entity.getPassword() == request.oldPassword()) {
                userRepository.update(entity.getId(), "password", request.newPassword());
            }
        }

    }

    private String generateUsername(String firstname, String lastname) {
        String baseUsername = firstname + "." + lastname;
        String finalUsername = baseUsername;
        int serialNumber = 0;

        while (!userRepository.findByProperty("username", finalUsername).isEmpty()) {
            serialNumber++;
            finalUsername = baseUsername + serialNumber;
        }

        return finalUsername;
    }

}
