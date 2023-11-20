package com.epam.crmgymhibernate.service.impl;

import com.epam.crmgymhibernate.dto.request.ActivateDeActivateUserRequest;
import com.epam.crmgymhibernate.dto.request.ChangePasswordRequest;
import com.epam.crmgymhibernate.dto.request.RegisterTraineeRequest;
import com.epam.crmgymhibernate.dto.request.RegisterTrainerRequest;
import com.epam.crmgymhibernate.dto.response.RegisterResponse;
import com.epam.crmgymhibernate.dto.universal.TrainingTypeDto;
import com.epam.crmgymhibernate.model.Trainee;
import com.epam.crmgymhibernate.model.Trainer;
import com.epam.crmgymhibernate.model.TrainingType;
import com.epam.crmgymhibernate.model.UserEntity;
import com.epam.crmgymhibernate.repository.TraineeRepository;
import com.epam.crmgymhibernate.repository.TrainerRepository;
import com.epam.crmgymhibernate.repository.TrainingTypeRepository;
import com.epam.crmgymhibernate.repository.UserRepository;
import com.epam.crmgymhibernate.service.AuthService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private TraineeRepository traineeRepository;
    @Mock
    private TrainerRepository trainerRepository;
    @Mock
    private TrainingTypeRepository trainingTypeRepository;


    @InjectMocks
    private AuthServiceImpl authService;

    @BeforeEach
    public void init() {

    }


    @Test
    void generatePassword() {
        String password = authService.generatePassword(10);

        System.out.println("Generated password: " + password);
        Assertions.assertEquals(10, password.length());
    }

    @Test
    void registerTrainee() {
        RegisterTraineeRequest request = new RegisterTraineeRequest(
                "Arda",
                "Guler",
                LocalDate.of(2005, 2, 25),
                "Turkey"
        );

        UserEntity userEntity = new UserEntity(
                11L,
                "Arda",
                "Guler",
                "Arda.Guler",
                "Turkey",
                "1324567890",
                true
        );

        Trainee traineeEntity = new Trainee();
        traineeEntity.setId(8L);
        traineeEntity.setDateOfBirth(request.dateOfBirth());
        traineeEntity.setUser(userEntity);


        when(userRepository.findByProperty("username", "Arda.Guler"))
                .thenReturn(Collections.emptyList());
        doNothing().when(userRepository).insert(any());
        doNothing().when(traineeRepository).insert(any());

        RegisterResponse registerResponse = authService.registerTrainee(request);

        Assertions.assertEquals(registerResponse.username(), traineeEntity.getUser().getUsername());

        // Verify interactions with repositories
        verify(userRepository, times(1)).insert(any(UserEntity.class));
        verify(userRepository, times(1)).findByProperty(anyString(), any());
        verify(traineeRepository, times(1)).insert(any(Trainee.class));
    }

    @Test
    void registerTrainer() {
        RegisterTrainerRequest request = new RegisterTrainerRequest(
                "Gavi",
                "Pablo",
                List.of(new TrainingTypeDto(1L, "Boxing"))
        );

        TrainingType trainingType = new TrainingType(1L, "Boxing");

        when(userRepository.findByProperty("username", "Gavi.Pablo"))
                .thenReturn(Collections.emptyList());
        doNothing().when(userRepository).insert(any(UserEntity.class));
        when(trainingTypeRepository.getById(eq(1L))).thenReturn(trainingType);
        doNothing().when(trainerRepository).insert(any(Trainer.class));


        RegisterResponse response = authService.registerTrainer(request);

        // Verify interactions with repositories
        verify(userRepository, times(1)).insert(any(UserEntity.class));
        verify(trainingTypeRepository, times(1)).getById(anyLong());
        verify(trainerRepository, times(1)).insert(any(Trainer.class));

        Assertions.assertEquals("Gavi.Pablo", response.username());
        Assertions.assertEquals(10, response.password().length());
    }

    @Test
    void activateDeActivateUser_success() {
        ActivateDeActivateUserRequest request = new ActivateDeActivateUserRequest(
                "eva.williams",
                false
        );

        UserEntity userEntity = new UserEntity(
                5L,
                "Eva",
                "Williams",
                "eva.williams",
                "202 Maple St",
                "password5",
                true
        );

        when(userRepository.findByProperty("username", "eva.williams"))
                .thenReturn(List.of(userEntity));

        doAnswer(invocationOnMock -> {
            userEntity.setActive(request.isActive());
            return null;
        }).when(userRepository).update(anyLong(), anyString(), eq(request.isActive()));


        authService.activateDeActivateUser(request);

        Assertions.assertEquals(request.isActive(), userEntity.isActive());

        verify(userRepository, times(1)).update(anyLong(), anyString(), anyBoolean());
        verify(userRepository, times(1)).findByProperty(anyString(), any());
    }

    @Test
    void activateDeActivateUser_fail_invalidUsername() {
        ActivateDeActivateUserRequest request = new ActivateDeActivateUserRequest(
                "unknown.user4040",
                true
        );

        when(userRepository.findByProperty("username", request.username()))
                .thenReturn(Collections.emptyList());

        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            authService.activateDeActivateUser(request);
        });

        verify(userRepository, times(0)).update(anyLong(), anyString(), anyBoolean());
        verify(userRepository, times(1)).findByProperty(anyString(), any());
    }

    @Test
    void changeUserPassword_fail_EntityNotFoundException() {
        ChangePasswordRequest request = new ChangePasswordRequest(
                "unknown.user4040",
                "random12Password",
                "random12Password"
        );

        when(userRepository.findByProperty("username", request.username())).thenReturn(Collections.emptyList());

        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            authService.changeUserPassword(request);
        });

        verify(userRepository, times(0)).update(anyLong(), anyString(), anyBoolean());
        verify(userRepository, times(1)).findByProperty(anyString(), any());
    }
}